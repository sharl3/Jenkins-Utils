import jenkins.model.*

// Nom du fichier de config
def jobName = "Mega-Integration-Continuous"
// Fichier de config dont tu as enlevé les sauts de ligne et echappé les guillemets
def configXml = "";

def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
Jenkins.instance.createProjectFromXML(jobName, xmlStream)






