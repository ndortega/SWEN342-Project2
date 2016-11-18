import akka.actor.{Actor, ActorRef}
import akka.actor.Actor.Receive

/**
  * This class represents a "line" in our airport screening system
  *
  */
class Line(
            id: Int,
            queue: ActorRef,
            baggageScanner: ActorRef,
            bodyScan: ActorRef,
            security: ActorRef  ) extends Actor{



  override def receive = {
    case x: String => println("Body Scan -> " + x);
    case SHUTDOWN => {
      println("Shutting down Line")

      // send shut down message to all components that make up the line
      queue ! SHUTDOWN
      baggageScanner !  SHUTDOWN
      bodyScan ! SHUTDOWN
      security ! SHUTDOWN

    }
  }
}
