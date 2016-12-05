/**
  * Created by nate on 11/17/16.
  */
class Message

  // Instead of java enums, we use case objects and case classes that can be sent
  // between the actors as messages. The "case" keyword makes this following object
  // or class able to be used in pattern matching.


  case class PASSENGER(id: Int) extends Message

  case object BODYSCANSHUTDOWN extends Message // one both are recieved by the security station, security signals that
  case object BAGSCANSHUTDOWN extends Message   // they "COMPLETED" to the Jail

  case object COMPLETED extends Message // sent by security system when completed
  case object PoisonPill


  case class BAGPASS(passenger: PASSENGER) extends Message
  case class BAGFAIL(passenger: PASSENGER) extends Message

  case class BODYPASS(passenger: PASSENGER) extends Message
  case class BODYFAIL(passenger: PASSENGER) extends Message

  case class BAG(passenger: PASSENGER) extends Message


