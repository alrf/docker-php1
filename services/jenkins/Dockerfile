FROM jenkins/jenkins:lts-alpine

USER root

RUN apk update && apk add --no-cache py-pip && pip install elasticsearch-curator

USER jenkins

RUN /usr/local/bin/install-plugins.sh workflow-aggregator job-dsl

COPY config/ /usr/share/jenkins/ref/init.groovy.d/

ENV JAVA_OPTS "-Djenkins.install.runSetupWizard=false"

