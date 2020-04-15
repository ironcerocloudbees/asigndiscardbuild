package com.cloudbees

import jenkins.model.Jenkins

def call(){
    sh """echo "Starting" """
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
        if (job.isBuildable() && job.supportsLogRotator()) {
            RED='asd'
            sh """echo "step1" """
            echo "Processing ${RED}"
            // adding a property implicitly saves so no explicit one
            try {
                job.setBuildDiscarder(new hudson.tasks.LogRotator ( 11, 11, 11, 11))
                printf "is updated"
            } catch (Exception e) {
                // Some implementation like for example the hudson.matrix.MatrixConfiguration supports a LogRotator but not setting it
                printf "[WARNING] Failed to update"
            }

        }
    }
    return;
}

