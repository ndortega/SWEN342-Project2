
import akka.actor.{Actor, ActorRef, PoisonPill}

import scala.util.Random

/**
  * Each body scanner is identified with the line it is in.
  * Passengers randomly fail inspection with a probability of 20%.
  */
class BodyScan(security: ActorRef, lineID: Int) extends Actor{

  override def receive = {
    case x: PASSENGER => {
      System.out.println("Body Scanner in line " + lineID + " received passenger " + x.id + ".")
      var msg: Message = null
      if (Random.nextInt(100) > 20) {
        System.out.println("Body Scanner in line " + lineID + " is sending passenger " + x.id + ", who passed, to security to wait for their bag.")
        msg = new BAGPASS(x)
      }
      else {
        System.out.println("Body Scanner in line " + lineID + " is sending passenger " + x.id + ", who failed, to security.")
        msg = new BAGFAIL(x)
      }
      security ! msg
    }

    case PoisonPill => {
      println("Shutting down Body Scanner")
      security ! COMPLETED
    }

  }
}
