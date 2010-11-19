package jarod.flash

object Flashpolicyd {

  def main(args: Array[String]) { 
	  val server = new FlashPolicyServer
	  server.startup
  }

}