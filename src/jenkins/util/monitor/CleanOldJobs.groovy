import hudson.model.*

def jobObsolesence = 6;

Hudson.instance.items.each{ job ->
	// First pass : is the job supposed to be deleted
	// Second pass : is the job older than "jobObsolesence" months 
}