import akka.actor.{Actor, ActorRef}

/**
  * Each baggage scanner is identified with the line it is in.
  * Baggage randomly fails inspection with a probability of 20%.
  */
class BaggageScan(security: ActorRef) extends Actor{
  override def receive = {
    case x: String => println("Baggage Scan -> " + x)
    case SHUTDOWN => println("Shutting down Baggage Scanner")
  }
}
