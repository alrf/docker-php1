import jenkins.*;
import jenkins.model.*;
import javaposse.jobdsl.dsl.DslScriptLoader
import javaposse.jobdsl.plugin.JenkinsJobManagement

def jobDslScript = new File('/usr/share/jenkins/ref/init.groovy.d/jobs.groovy')
def jobManagement = new JenkinsJobManagement(System.out, [:], new File('.'))

new DslScriptLoader(jobManagement).runScript(jobDslScript.text)
