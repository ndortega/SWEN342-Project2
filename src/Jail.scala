import akka.actor.Actor


/**
  * The jail knows how many security stations feed it passengers as prisoners.
  * The jail will hold all prisoners until the end of the day when all prisoners in holding are sent to permanent detention.
  */
class Jail extends Actor{
  override def receive = {
    case x: String => println("Jail -> " + x);
    case SHUTDOWN => println("sending all inmates to prison")
  }
}
