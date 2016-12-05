import akka.actor.{Actor, ActorRef, PoisonPill}
import akka.actor.Actor.Receive

/**
  * This class represents a "line" in our airport screening system
  *
  */
class Line( id: Int,
            queue: ActorRef,
            baggageScanner: ActorRef,
            bodyScan: ActorRef,
            security: ActorRef  ) extends Actor{



  override def receive = {
    case x: PASSENGER => queue ! x
    case PoisonPill => {
      println("Shutting down Line")
      // send shut down message to all components that make up the line
      queue ! PoisonPill
    }
  }
}
