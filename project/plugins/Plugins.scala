// vim: set ts=4 sw=4 et:
import sbt._

class Plugins(info: ProjectInfo) extends PluginDefinition(info) {
    lazy val eclipse = "de.element34" % "sbt-eclipsify" % "[0.7.0, )"
}
