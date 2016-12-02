/**
  * Created by nate on 11/17/16.
  */
class Message

  // Instead of java enums, we use case objects and case classes that can be sent
  // between the actors as messages. The "case" keyword makes this following object
  // or class able to be used in pattern matching.

  case object SHUTDOWN extends Message
  case class PASSENGER(id: Int) extends Message

  case class BAGPASS(passenger: PASSENGER) extends Message
  case class BAGFAIL(passenger: PASSENGER) extends Message

  case class BODYPASS(passenger: PASSENGER) extends Message
  case class BODYFAIL(passenger: PASSENGER) extends Message

  case class BAG(passenger: PASSENGER) extends Message


