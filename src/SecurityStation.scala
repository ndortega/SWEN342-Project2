import java.util

import akka.actor.{Actor, ActorRef}

/**
  * Each security station is identified with the line it is in.
  * The security station must be prepared to have either the body scanner or the baggage scanner get ahead of the other by an arbitrary amount - that is, it must remember results until both scan reports for a passenger arrive.
  * The security station cannot close until both of the scanners attached have turned off.
  */
class SecurityStation(jail: ActorRef) extends Actor{
  val passPassenger = new util.LinkedList[PASSENGER]()
  val failPassenger = new util.LinkedList[PASSENGER]()

  val passBagLine = new util.LinkedList[BAGPASS]()
  val failBagLine = new util.LinkedList[BAGFAIL]()

  override def receive = {
    case x: BODYPASS => {
      var bagToRemove:BAGPASS = null
      var foundBag = false
      passBagLine.forEach( bag => {
        if (bag.passenger.id == x.passenger.id){
            bagToRemove = bag
          foundBag = true
        }
      })
      passBagLine.remove(bagToRemove)
      if(!foundBag){
        passPassenger.add(x.passenger)
      }
    }

    case x: BAGPASS => {
      var passengerToRemove: PASSENGER = null
      var foundPass = false
      passPassenger.forEach( passenger => {
        if (passenger.id == x.passenger.id){
          passengerToRemove = passenger
          foundPass = true
        }
      })
      passPassenger.remove(passengerToRemove)
      if(!foundPass){
        passBagLine.add(x)
      }
    }


    case x: BAGFAIL => {
      failBagLine.add(x)
      var passengerFailed = false
      failPassenger.forEach( passenger => {
        if(passenger.id == x.passenger.id){
          passengerFailed = true
        }
      })
      if(!passengerFailed){
        jail ! x.passenger
      }
    }

    case x: BODYFAIL => {
      failPassenger.add(x.passenger)
      var bagFailed = false
      failBagLine.forEach( bag => {
        if(bag.passenger.id == x.passenger.id){
          bagFailed = true
        }
      })
      if(!bagFailed){
        jail ! x.passenger
      }
    }

    case x: String => println("security station -> " + x);
    case SHUTDOWN => println("Shutting down security station")
  }
}
