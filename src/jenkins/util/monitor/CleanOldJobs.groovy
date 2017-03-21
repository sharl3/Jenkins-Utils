import hudson.model.*

def blacklistViews = ["__BLACK_LIST", "zOffline - Prima RE", "Prima Repository","Prima","Autres","IBCS","Maven","CustomerCare"] as ArrayList;
def List<String> blacklistItems = new ArrayList<String>();
def JOB_OBSOLESENCE = 6;
def Calendar milestone = Calendar.instance;
milestone.add(Calendar.MONTH, -JOB_OBSOLESENCE);
def TO_DELETE_VIEW =Hudson.instance.getView("TO_DELETE");


println "Deleting jobs in the TO_DELETE view";
// Delete all the jobs in the TO_DELETE view
TO_DELETE_VIEW.items.each() {
	println "\t${it.fullDisplayName}";
	// it.delete();
  }
  
// Loading the jobs in the blacklisted views
println "Blacklisted jobs : ";
blacklistViews.each{viewName ->
	print "\tView ${viewName} :";
	Hudson.instance.getView(viewName).items.each{job ->
		print " ${job.fullDisplayName} "
		blacklistItems.add(job.name);
	}
	println ".";
}

println "Moving obsolete jobs to the TO_DELETE view";
Hudson.instance.items.each{ job ->
	// if the job is part of the "blackList" job list, skip any action
	if(blacklistItems.contains(job.name)){
		println "${job.fullDisplayName} is blacklisted";
	}
	else{
		if(job.getLastBuild() == null){
			println "Moving ${job.fullDisplayName} (never executed)";
			//TO_DELETE_VIEW.add(job);
		} else {
			def Calendar lastExecution = job.getLastBuild().getTimestamp();
			if (lastExecution.before(milestone)){
				println "Moving ${job.fullDisplayName} (last build : ${lastExecution.get(Calendar.YEAR)}-${lastExecution.get(Calendar.MONTH)+1}-${lastExecution.get(Calendar.DATE)})";
				//TO_DELETE_VIEW.add(job);
			}
		}
	}
}
