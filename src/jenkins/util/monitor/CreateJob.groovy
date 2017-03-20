import jenkins.model.*

def jobName = "___TestReloadedJob"
def configXml = "<?xml version='1.0' encoding='UTF-8'?><project>  <actions/>  <description>Daily workspace cleanup.</description>  <keepDependencies>false</keepDependencies>  <properties/>  <scm class=\"hudson.scm.NullSCM\"/>  <canRoam>true</canRoam>  <disabled>false</disabled>  <blockBuildWhenDownstreamBuilding>false</blockBuildWhenDownstreamBuilding>  <blockBuildWhenUpstreamBuilding>false</blockBuildWhenUpstreamBuilding>  <jdk>(Default)</jdk>  <triggers>    <hudson.triggers.TimerTrigger>      <spec>H 07 * * *</spec>    </hudson.triggers.TimerTrigger>  </triggers>  <concurrentBuild>false</concurrentBuild>  <builders>    <hudson.plugins.groovy.SystemGroovy plugin=\"groovy@1.27\">      <scriptSource class=\"hudson.plugins.groovy.StringScriptSource\">        <command>import hudson.model.*// For each projectfor(item in Hudson.instance.items) {  // check that job is not building  if(!item.isBuilding()) {    println(&quot;Wiping out workspace of job &quot;+item.name)    item.doDoWipeOutWorkspace()  }  else {    println(&quot;Skipping job &quot;+item.name+&quot;, currently building&quot;)  }}</command>      </scriptSource>      <bindings></bindings>      <classpath></classpath>    </hudson.plugins.groovy.SystemGroovy>  </builders>  <publishers>    <hudson.plugins.emailext.ExtendedEmailPublisher plugin=\"email-ext@2.40.5\">      <recipientList>pj-it@prima-solutions.com, are@prima-solutions.com, hmn@prima-solutions.com</recipientList>      <configuredTriggers>        <hudson.plugins.emailext.plugins.trigger.AlwaysTrigger>          <email>            <recipientList></recipientList>            <subject>\$PROJECT_DEFAULT_SUBJECT</subject>            <body>\$PROJECT_DEFAULT_CONTENT</body>            <recipientProviders>              <hudson.plugins.emailext.plugins.recipients.ListRecipientProvider/>            </recipientProviders>            <attachmentsPattern></attachmentsPattern>            <attachBuildLog>false</attachBuildLog>            <compressBuildLog>false</compressBuildLog>            <replyTo>\$PROJECT_DEFAULT_REPLYTO</replyTo>            <contentType>project</contentType>          </email>        </hudson.plugins.emailext.plugins.trigger.AlwaysTrigger>      </configuredTriggers>      <contentType>default</contentType>      <defaultSubject>[ _|3/V|&lt;!/V\$ ] Daily workspace cleanup logs.</defaultSubject>      <defaultContent>Please find attached today&apos;s workspaces cleanup log.Yours sincerely,Jenkins</defaultContent>      <attachmentsPattern></attachmentsPattern>      <presendScript>\$DEFAULT_PRESEND_SCRIPT</presendScript>      <attachBuildLog>true</attachBuildLog>      <compressBuildLog>false</compressBuildLog>      <replyTo>\$DEFAULT_REPLYTO</replyTo>      <saveOutput>false</saveOutput>      <disabled>false</disabled>    </hudson.plugins.emailext.ExtendedEmailPublisher>  </publishers>  <buildWrappers/></project>";

def xmlStream = new ByteArrayInputStream( configXml.getBytes() )

Jenkins.instance.createProjectFromXML(jobName, xmlStream)