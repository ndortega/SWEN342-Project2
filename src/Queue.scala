import akka.actor.Actor

/**
  * Each queue is identified with the line it is in.
  */
class Queue extends Actor{
  override def receive = {
    case x: String => println("Queue -> " + x);
    case SHUTDOWN => println("Shutting down Queue")
  }
}
