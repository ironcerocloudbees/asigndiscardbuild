package com.cloudbees

import jenkins.model.Jenkins

def call(){
    sh 'echo "Starting"'
    Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
        if (job.isBuildable() && job.supportsLogRotator()) {
            def fullDisplayName = job.fullDisplayName.toString()
            def fullName = job.fullName.toString()
            sh 'echo "Processing"'
                // adding a property implicitly saves so no explicit one
                try {
                    job.setBuildDiscarder(new hudson.tasks.LogRotator ( 10, 10, 10, 10))
                    sh 'echo "is updated"'
                } catch (Exception e) {
                    // Some implementation like for example the hudson.matrix.MatrixConfiguration supports a LogRotator but not setting it
                    sh 'echo "[WARNING] Failed to update"'
                }

        }
    }
    return;
}

