import hudson.model.*

def blacklist = ["PrimaRE-"] as ArrayList;
def JOB_OBSOLESENCE = 6;
def Calendar milestone = Calendar.instance;
milestone.add(Calendar.MONTH, -JOB_OBSOLESENCE);
def TO_DELETE_VIEW =Hudson.instance.getView("TO_DELETE");


println ("Milestone : " + (milestone.get(Calendar.MONTH) + 1) + "-" + milestone.get(Calendar.DATE) + "-" + milestone.get(Calendar.YEAR));


// Delete all the jobs in the TO_DELETE view
TO_DELETE_VIEW.items.each() {
	println "Deleting ${it.fullDisplayName}";
	// it.delete();
  }

Hudson.instance.items.each{ job ->
	def jobName = job.name;
	// if the job is part of the "blackList" job list, skip any action
	if(!jobName.contains(blacklist.get(0))){
		// if the job supposed to be deleted (aka its name contains "TO_DELETE")
		// then we delete it
		if(jobName.contains("TO_DELETE"))
			TO_DELETE_VIEW.add(job);
		// if the job is older than "jobObsolesence" months
		// then we prefix it with "TO_DELETE" so next time this algorithm is executed it is actually deleted
		else if(job.getLastBuild() != null){
			if (job.getLastBuild().getTimestamp()?.before(milestone))
				println "${jobName} was last built more than 6 months ago";
		}
	}
}

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

println("The build is run at ${now.time}");

for (item in jenkins.items){
	println("\t ${item.name}");
	// Ignore project that contains freeze or patch case insensitive
	if (item.name ==~ /(?i)(freeze|patch).*/){
		println("\t Ignored as it is a freeze or patch build");
	}else if (!item.disabled&&item.getLastBuild()!=null){
		build_time=item.getLastBuild().getTimestamp();
		if (now.time.time/1000-build_time.time.time/1000>oneDayInSecond){
			println("\t\tLast build was built in more than ${hour} hours ago");
			println("\t\tLast built was at ${build_time.time}");
			list<< item;
		}
	}else if (item.disabled){
		println("\t\tProject is disabled");
	}
}

if (list.size()>0){
	println("Please take a look at following projects:");
	for (item in list){
		println("\t ${item.name}");
	}
	return 1;
}