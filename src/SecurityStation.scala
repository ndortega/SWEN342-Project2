import akka.actor.{Actor, ActorRef}

import scala.collection.mutable.ArrayBuffer

/**
  * Each security station is identified with the line it is in.
  * The security station must be prepared to have either the body scanner or the baggage scanner get ahead of the other by an arbitrary amount - that is, it must remember results until both scan reports for a passenger arrive.
  * The security station cannot close until both of the scanners attached have turned off.
  */
class SecurityStation(jail: ActorRef) extends Actor{

  override def receive = {
    case x: String => println("security station -> " + x);
    case SHUTDOWN => println("Shutting down security station")
  }
}
