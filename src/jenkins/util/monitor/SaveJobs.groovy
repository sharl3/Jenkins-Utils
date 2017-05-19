import hudson.model.*
import sun.misc.FpUtils
import hudson.FilePath

Hudson.instance.items.each{ job ->
	if(build.workspace.isRemote()) {
		channel = build.workspace.channel;
		fp = new FilePath(channel, build.workspace.toString() + "/${job.name}.xml")
		println "Creating file " + fp.getName() + " remotely on " + build.workspace.toString();
	} else {
		fp = new FilePath(new File(build.workspace.toString() + "/${job.name}.xml"));
		println "Creating file " + fp.getName() + " locally on " + build.workspace.toString();
	}
	fp.write(job.getConfigFile().asString(), null);
}