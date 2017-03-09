 
jenkins = Hudson.instance
now=Calendar.instance;


println("The build run at ${now.time}");
println("Job's name;Disabled;Last execution date;Last build status;Evolution")

List<List<String>> jobsStatuses = new ArrayList<List<String>>();
for (item in jenkins.items){
	List<String> text = new ArrayList<String>();
	text.add(item.name);
	text.add(item.disabled);
	lastBuild = item.getLastBuild();
   if (lastBuild!=null){
	  // lastExecution #
	  text.add(lastBuild.getTimestamp().getTime().format("yyyy-MM-dd"));
	  // result 
	  text.add(lastBuild.getResult().toString());
	  // evolution 
	  text.add(lastBuild.getBuildStatusSummary().message);
	}
	jobsStatuses.add(text);
}
jobsStatuses.each {
	it.each {
		print("${it};")
	}
	println();
}
