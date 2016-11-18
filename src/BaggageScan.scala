import akka.actor.Actor

/**
  * Each baggage scanner is identified with the line it is in.
  * Baggage randomly fails inspection with a probability of 20%.
  */
class BaggageScan extends Actor{
  override def receive = {
    case x: String => println("Baggage Scan -> " + x);
  }
}
