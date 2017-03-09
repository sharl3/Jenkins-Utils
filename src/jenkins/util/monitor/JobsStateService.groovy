 
jenkins = Hudson.instance
now=Calendar.instance;


println("The build run at ${now.time}");
println("Job's name;Disabled;Last execution date;Last build status;Evolution")

List<List<String>> jobsStatuses = new ArrayList<List<String>>();

for (job in jenkins.items){
	List<String> jobStatuts = new ArrayList<String>();
	jobStatuts.add(job.name);
	jobStatuts.add(job.disabled);
	lastBuild = job.getLastBuild();
   if (lastBuild!=null){
	  // lastExecution #
	  jobStatuts.add(lastBuild.getTimestamp().getTime().format("yyyy-MM-dd"));
	  // result 
	  jobStatuts.add(lastBuild.getResult().toString());
	  // evolution 
	  jobStatuts.add(lastBuild.getBuildStatusSummary().message);
	}
	jobsStatuses.add(jobStatuts);
}
jobsStatuses.each {
	it.each {
		print("${it};")
	}
	println();
}
