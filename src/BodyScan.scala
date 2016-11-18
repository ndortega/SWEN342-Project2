
import akka.actor.{Actor, ActorRef}

/**
  * Each body scanner is identified with the line it is in.
  * Passengers randomly fail inspection with a probability of 20%.
  */
class BodyScan(security: ActorRef) extends Actor{

  override def receive = {
    case x: String => println("Body Scan -> " + x);
    case SHUTDOWN => println("Shutting down Body Scanner")
  }
}
