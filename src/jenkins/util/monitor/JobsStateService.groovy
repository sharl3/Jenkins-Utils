import hudson.model.*
import hudson.node_monitors.*
import hudson.slaves.*
import java.util.concurrent.*
jenkins = Hudson.instance
// Define hour to compare (hour=24 will find builds that were built more than 1 day ago)
hour=24;
minute=60;
second=60;
oneDayInSecond=hour*minute*second;
now=Calendar.instance;
list=[];

println("The build run at ${now.time}");
println("Job's name;Disabled;Last build date;Last build status")
for (item in jenkins.items){
  String text = "${item.name};${item.disabled};";
   if (item.getLastBuild()!=null){
	  lastBuild = item.getLastBuild();
	  String lastExecution = lastBuild.getTimestamp().getTime().format("yyyy-MM-dd");
	  String evolution = lastBuild.getBuildStatusSummary().message;
	  String result = lastBuild.getResult().toString();
	  text = "${text}${lastExecution};${result};${evolution}";
	}
	println(text);
}
