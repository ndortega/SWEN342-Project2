import akka.actor.{Actor, ActorRef}

/**
  * Each queue is identified with the line it is in.
  */
class Queue(baggageScan: ActorRef, bodyScan: ActorRef, lineID: Int) extends Actor{
  override def receive = {
    case x: PASSENGER => {
      System.out.println("Queue in line " + lineID + " received passenger " + x.id + ".")
      System.out.println("Queue in line " + lineID + " is sending passenger " + x.id + " to the body scanner to be scanned.")
      bodyScan ! x
      System.out.println("Queue in line " + lineID + " is sending passenger " + x.id + "'s bags to the bag scanner to be scanned.")
      baggageScan ! new BAG(x)
    }
    case x: String => println("Queue -> " + x);
    case SHUTDOWN => println("Shutting down Queue")
  }
}
