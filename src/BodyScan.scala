
import akka.actor.{Actor, ActorRef}

import scala.util.Random

/**
  * Each body scanner is identified with the line it is in.
  * Passengers randomly fail inspection with a probability of 20%.
  */
class BodyScan(security: ActorRef) extends Actor{

  override def receive = {
    case x: PASSENGER => {
      val msg = if (Random.nextInt(100) > 20) new BODYPASS(x) else new BODYFAIL(x)
      security ! msg
    }

    case x: String => println("Body Scan -> " + x);
    case SHUTDOWN => println("Shutting down Body Scanner")
  }
}
