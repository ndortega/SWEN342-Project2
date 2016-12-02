

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable
import scala.util.Random

/**
  * Each baggage scanner is identified with the line it is in.
  * Baggage randomly fails inspection with a probability of 20%.
  */
class BaggageScan(security: ActorRef) extends Actor{
  override def receive = {
    case x: BAG => {
      val msg = if (Random.nextInt(100) > 20) new BAGPASS(x.passenger) else new BAGFAIL(x.passenger)
      security ! msg
    }
    case x: String => println("Baggage Scan -> " + x)
    case SHUTDOWN => println("Shutting down Baggage Scanner")
  }
}
