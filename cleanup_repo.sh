rm -rf logs
rm -rf data/elk/nodes
rm -rf data/redis

docker ps -qa --no-trunc --filter "status=exited" | xargs docker rm -f
docker images -q | xargs docker rmi -f
