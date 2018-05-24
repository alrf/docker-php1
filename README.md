
### Multi-container Docker application with Docker Compose that contain services:

- 1 Redis container
- 2 Apache containers
    - serve one index.php file that
   	 - outputs a json containing the current date and the IP of the user
   	 - sets a session variable called `count` that is incremented on each visit
    - save all session information in the Redis container
    - defer all interpreting of PHP code to the PHP-FPM container
    - have error logging and access logging enabled
    - logs need to be sent to the Logstash container
- 1 PHP-FPM container
    - PHP7.1
    - used for interpreting PHP code from the Apache containers
- 1 Load Balancer for the Apache containers
- 1 Jenkins container
    - has a job that runs once per day and deletes indexes older than 30 days from Elasticsearch
- 1 Elasticsearch container
- 1 Kibana container
    - allows viewing the data from the Elasticsearch container
- 1 Logstash container
    - ingests access and error logs from the Apache containers and saves to Elasticsearch

### Bonuses:

- PHP json output contains `count` variable
- Jenkins has a second job (`Create_Kibana_Index`) - for create Kibana index
- jenkins first job (`Cleanup_Index`) output contains Elasticsearch indexes
- Cleanup sh-script for delete logs, db, docker containers and images, run as: `sudo bash cleanup_repo.sh`

### Build env:

- Server (based on Linux) with 4 Gb RAM (mostly for Elasticsearch running)
- Docker version 18.03.1
- Docker-compose version 1.21.2

### Usage:

Start app:  
For first run use `sudo chown 1000 data/elk` and `docker-compose up -d` (see Troubleshooting section #2)  
For others `docker-compose up -d`  

Apache Loadbalancer (PHP output): `http://<your_ip>`  
Kibana: `http://<your_ip>:5601`  
Jenkins: `http://<your_ip>:8080`  

Stop app:  
`docker-compose down`

### Troubleshooting:

1. You can come across with error for Elasticsearch: `max virtual memory areas vm.max_map_count [65530] likely too low, increase to at least [262144]`  
Fix: `sudo sysctl -w vm.max_map_count=262144`  
Create a file named `60-elasticsearch.conf` and place it in `/etc/sysctl.d/` with the following content `vm.max_map_count=262144`

2. The [unprivileged `elasticsearch` user][esuser] is used within the Elasticsearch image, therefore the
  mounted data directory must be owned by the uid `1000`.  
  So, change owner by `sudo chown 1000 data/elk` before run.

[esuser]: https://github.com/elastic/elasticsearch-docker/blob/016bcc9db1dd97ecd0ff60c1290e7fa9142f8ddd/templates/Dockerfile.j2#L22
