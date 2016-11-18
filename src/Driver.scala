import akka.actor.{ActorSystem, Props}


/**
  * At the beginning of the day, the system will initialize and turn on all of the scanners.
  *
  * At the end of the day, the system closes by turning off all the scanners, and sending prisoners
  * in jail to permanent detention facilities. As passengers and their bags clear through the system,
  * it will shutdown, i.e. the queue must be empty and the body scanner must be ready before the body
  * scanner is turned off.
  */
object Driver {

  def main(args: Array[String]): Unit = {


    val system = ActorSystem.create()

    // Creates the two main actors in the system
    val bodyScanActor = system.actorOf(Props(new BodyScan))
    val baggageActor = system.actorOf(Props(new BaggageScan))

    // shows how you send sample messages to the actors
    bodyScanActor ! "my message"
    baggageActor ! "other message"

    // shuts down the actor system
    system.terminate()
  }

}