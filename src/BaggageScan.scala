

import akka.actor.{Actor, ActorRef}

import scala.collection.mutable
import scala.util.Random

/**
  * Each baggage scanner is identified with the line it is in.
  * Baggage randomly fails inspection with a probability of 20%.
  */
class BaggageScan(security: ActorRef, lineID: Int) extends Actor{
  override def receive = {
    case x: BAG => {
      System.out.println("Bag Scanner in line " + lineID + " received passenger " + x.passenger.id + "'s bags.")
      var msg: Message = null
        if (Random.nextInt(100) > 20) {
          System.out.println("Bag Scanner in line " + lineID + " is sending passenger " + x.passenger.id + "'s bags, which passed, to security to wait for their passenger.")
          msg = new BAGPASS(x.passenger)
        }
        else {
          System.out.println("Bag Scanner in line " + lineID + " is sending passenger " + x.passenger.id + "'s bags, which failed, to security.")
          msg = new BAGFAIL(x.passenger)
        }
      security ! msg
    }
    case x: String => println("Baggage Scan -> " + x)
    case SHUTDOWN => println("Shutting down Baggage Scanner")
  }
}
