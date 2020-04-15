package com.cloudbees

import jenkins.model.Jenkins

@Mixin(Config)
class AsignDiscargBuildUtil implements Serializable{
    
    def steps
    
    AsignDiscargBuildUtil(steps) {this.steps = steps}
    
    def call(){
        Jenkins.instanceOrNull.allItems(hudson.model.Job).each { job ->
            if (job.isBuildable() && job.supportsLogRotator() && job.getProperty(jenkins.model.BuildDiscarderProperty) == null) {
                steps.println "Processing \"${job.fullDisplayName}\""
                    // adding a property implicitly saves so no explicit one
                    try {
                        job.setBuildDiscarder(new hudson.tasks.LogRotator ( 3, 4, 5, 6))
                        steps.println "${job.fullName} is updated"
                    } catch (Exception e) {
                        // Some implementation like for example the hudson.matrix.MatrixConfiguration supports a LogRotator but not setting it
                        steps.println "[WARNING] Failed to update ${job.fullName} of type ${job.class} : ${e}"
                    }

            }
        }
        return;
    }
}
