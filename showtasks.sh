#!/usr/bin/env bash

export PROJECT_PATH="$( pwd )"
export CATALINA_HOME=/Users/mohhio/Desktop/kodillaProjects/apache-tomcat-9.0.21

build_deploy(){
    if $PROJECT_PATH/runcrud.sh; then
        echo "build and deployment successful"
    else
        echo "cannot build or deploy the project"
    fi
}

show_tasks(){
    if timeout 10 open -a safari & osascript -e 'tell application "Safari" to open location "http://localhost:8080/crud/v1/task/getTasks"'; then
        echo "seccess"
    else
        echo "timeout reached"
    fi
}

stop(){
    $CATALINA_HOME/bin/catalina.sh stop
}

build_deploy
show_tasks
stop