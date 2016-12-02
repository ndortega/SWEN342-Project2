import akka.actor.{Actor, ActorRef}

/**
  * Each queue is identified with the line it is in.
  */
class Queue(baggageScan: ActorRef, bodyScan: ActorRef) extends Actor{
  override def receive = {
    case x: PASSENGER => bodyScan ! x ; baggageScan ! new BAG(x)
    case x: String => println("Queue -> " + x);
    case SHUTDOWN => println("Shutting down Queue")
  }
}
