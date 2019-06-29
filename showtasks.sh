#!/usr/bin/env bash

export PROJECT_PATH="$( pwd )"

build_deploy(){
    if $PROJECT_PATH/runcrud.sh; then
        echo "build and deployment successful"
    else
        echo "cannot build or deploy the project"
    fi
}

show_tasks(){
    open http://localhost:8080/crud/v1/task/getTasks
}

build_deploy
show_tasks