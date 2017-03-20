import hudson.model.*

def blacklist = ["PrimaRE-"] as ArrayList;
def BigDecimal JOB_OBSOLESENCE = 6;
def BigDecimal SECONDS_PER_MONTH = 30 * 24 * 60 * 60;
def BigDecimal now_in_seconds = Calendar.instance.time.time/1000;
def BigDecimal OBSOLESENCE_IN_SECONDS = SECONDS_PER_MONTH.multiply(JOB_OBSOLESENCE);

println "Obsolesence in seconds : ${OBSOLESENCE_IN_SECONDS}"
println "Now in seconds : ${now_in_seconds}" 


def TO_DELETE_VIEW =Hudson.instance.getView("TO_DELETE");

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
			def BigDecimal lastBuildTimeStampInSeconds = job.getLastBuild().getTimestamp().time.time / 1000;
			if (now_in_seconds.minus(lastBuildTimeStampInSeconds) > OBSOLESENCE_IN_SECONDS)
				println "${jobName} was last build ${lastBuildTimeStampInSeconds.div(SECONDS_PER_MONTH)} month ago)"
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