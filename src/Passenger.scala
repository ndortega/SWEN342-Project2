import akka.actor.Actor

/**
  * Passengers enter the system from a main driver program.
  * Passengers are randomly turned away for document problems at a probability of 20%.
  * Passengers not turned away enter the queue for one of the lines in a cyclic fashion.
  * Passengers can go to the body scanner only when it is ready.
  * Passengers place their baggage in the baggage scanner as soon as they enter a queue.
  * If a passenger and his or her baggage are both passed, the passenger leaves the system. Otherwise, the passenger goes to jail.
  */
class Passenger extends Actor {

  override def receive: Receive = {
    case _=> "default"
  }
}
