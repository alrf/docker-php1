pipelineJob('Create_Kibana_Index') {
  definition {
    cps {
      sandbox()
      script("""
        node {
          stage('Create') {
            sh(returnStdout: true, script: "sleep 5 && curl lb; curl -XPOST -D- 'http://kibana:5601/api/saved_objects/index-pattern' -H 'Content-Type: application/json' -H 'kbn-version: 6.2.3' -d '{\\"attributes\\":{\\"title\\":\\"apache-*\\",\\"timeFieldName\\":\\"@timestamp\\"}}'").trim()
          }
        }
      """.stripIndent())
    }
  }
}

pipelineJob('Cleanup_Index') {
  definition {
    cps {
      sandbox()
      script("""
        node {
          stage('Show') {
            sh 'curator_cli --host elasticsearch --port 9200 show_indices'
          }
          stage('Delete') {
            sh(returnStdout: true, script: "curator_cli --host elasticsearch --port 9200 delete_indices --filter_list '[{\\"filtertype\\":\\"age\\",\\"source\\":\\"name\\",\\"direction\\":\\"older\\",\\"unit\\":\\"days\\",\\"unit_count\\":30,\\"timestring\\":\\"%Y.%m.%d\\"}]'").trim()
          }
        }
      """.stripIndent())
    }
  }
  triggers {
      cron('@midnight')
  }
}

