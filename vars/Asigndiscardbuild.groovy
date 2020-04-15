package com.cloudbees

import jenkins.model.Jenkins

def call(){
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
        if (job.isBuildable() && job.supportsLogRotator() && job.getProperty(jenkins.model.BuildDiscarderProperty) == null) {
            sh 'echo "Processing \"${job.fullDisplayName}\""'
                // adding a property implicitly saves so no explicit one
                try {
                    job.setBuildDiscarder(new hudson.tasks.LogRotator ( 10, 10, 10, 10))
                    sh 'echo "${job.fullName} is updated"'
                } catch (Exception e) {
                    // Some implementation like for example the hudson.matrix.MatrixConfiguration supports a LogRotator but not setting it
                    sh 'echo "[WARNING] Failed to update ${job.fullName} of type ${job.class} : ${e}"'
                }

        }
    }
    return;
}

