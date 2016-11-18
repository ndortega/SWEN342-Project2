/**
  * Created by nate on 11/17/16.
  */
class Message

  // Instead of java enums, we use case objects that can be used in pattern matching
  case object SHUTDOWN extends Message
  case class PASSENGER(id: Int)
  case class PASS(passenger: PASSENGER) extends Message
  case class FAIL(passenger: PASSENGER) extends Message
  case class BAG(passenger: PASSENGER) extends Message


