// vim: set ts=4 sw=4 et:
import sbt._
import de.element34.sbteclipsify._

class FlashpolicydProject(info: ProjectInfo) extends DefaultProject(info) with Eclipsify {

    val mavenLocal = "Local Maven Repository" at "file://"+Path.userHome+"/.m2/repository"

    val nexusRepo = "nexus repo" at "http://192.168.0.19:6666/nexus/content/groups/public"
    val mavenRepo2 = "maven repo 2" at "http://repo2.maven.org/maven2/"
    val jbossRepo = "jboss" at "https://repository.jboss.org/nexus/content/groups/public/"
    val jolboxRepo = "jolbox" at "http://jolbox.com/bonecp/downloads/maven/"

    override def libraryDependencies = Set(
        "org.jboss.netty" % "netty" % "[3.2, )" % "compile"
    ) ++ super.libraryDependencies

    override def compileOptions = super.compileOptions ++ Seq(Optimize)

    override def mainClass = Some("jarod.flash.Flashpolicyd")
}
