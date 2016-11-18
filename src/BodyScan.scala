import akka.actor.Actor

/**
  * Each body scanner is identified with the line it is in.
  * Passengers randomly fail inspection with a probability of 20%.
  */
class BodyScan extends Actor{

  override def receive = {
    case x: String => println("Body Scan -> " + x);
  }
}
