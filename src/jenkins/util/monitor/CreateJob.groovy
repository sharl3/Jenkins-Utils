import jenkins.model.*

// Nom du fichier de config
def jobName = "Mega-Integration-Continuous"
// Fichier de config dont tu as enlev� les sauts de ligne et echapp� les guillemets
def configXml = "";

def xmlStream = new ByteArrayInputStream( configXml.getBytes() )
Jenkins.instance.createProjectFromXML(jobName, xmlStream)






