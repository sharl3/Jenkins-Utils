 
jenkins = Hudson.instance
now=Calendar.instance;


println("The build run at ${now.time}");
println("Job's name;Job's Status;Last build result;Last execution date;Evolution")

List<List<String>> jobsStatuses = new ArrayList<List<String>>();

jenkins.items.each{ job -> 
	List<String> jobStatus = new ArrayList<String>();
	jobStatus.add(job.name);
	jobStatus.add(job.disabled?"DISABLED":"ENABLED");
	lastBuild = job.getLastBuild();
   if (lastBuild!=null){
	   // result 
	   jobStatus.add(lastBuild.getResult().toString());
	  // lastExecution 
	  jobStatus.add(lastBuild.getTimestamp().getTime().format("yyyy-MM-dd"));
	  // evolution 
	  jobStatus.add(lastBuild.getBuildStatusSummary().message);
	} else {
		jobStatus.add("NEVER_RUN");
	}
	jobsStatuses.add(jobStatus);
}
jobsStatuses.each {
	it.each {
		print("${it};")
	}
	println();
}
