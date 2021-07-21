import jenkins.model.Jenkins
import hudson.model.Job

    BUILDS_TO_KEEP = 0
    def tenDaysAgo=new Date() -10
    Jenkins.instance.getAllItems(Job.class).each { job->
    println job.name
    def recent=job.builds.limit(BUILDS_TO_KEEP)
    def buildsToDelete = job.builds.findAll {
    !recent.contains(it) && ! (it.getTime() > tenDaysAgo)
  }
  if(!buildsToDelete){
      println "No Build older than 10 days"
  }
    for(build in buildsToDelete){
        if(!recent.contains(build)){
            def artifacts=build.artifacts
            if(artifacts){
                println "Deleting : " + artifacts
                build.deleteArtifacts()
           }
           else{
               println "No artifacts present"
           }
           
        }
    }
}

