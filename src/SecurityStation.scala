import java.util

import akka.actor.{Actor, ActorRef}

/**
  * Each security station is identified with the line it is in.
  * The security station must be prepared to have either the body scanner or the baggage scanner get ahead of the other by an arbitrary amount - that is, it must remember results until both scan reports for a passenger arrive.
  * The security station cannot close until both of the scanners attached have turned off.
  */
class SecurityStation(jail: ActorRef, lineID: Int) extends Actor{
  val passPassenger = new util.LinkedList[PASSENGER]()
  val failPassenger = new util.LinkedList[PASSENGER]()

  val passBagLine = new util.LinkedList[BAGPASS]()
  val failBagLine = new util.LinkedList[BAGFAIL]()

  override def receive = {
    case x: BODYPASS => {
      System.out.println("Security Station in line " + lineID + " received passenger " + x.passenger.id + " who passed their scan.")
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
      else{
        System.out.println("Passenger " + x.passenger.id + " has passed their scans and is leaving security from line " + lineID+ ".")
      }
    }

    case x: BAGPASS => {
      System.out.println("Security Station in line " + lineID + " received passenger " + x.passenger.id + "'s bags which passed its scan.")
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
      else{
        System.out.println("Passenger " + x.passenger.id + " has passed their scans and is leaving security from line " + lineID + ".")
      }
    }


    case x: BAGFAIL => {
      System.out.println("Security Station in line " + lineID + " received passenger " + x.passenger.id + "'s bags which failed its scan.")
      failBagLine.add(x)
      var passengerFailed = false
      failPassenger.forEach( passenger => {
        if(passenger.id == x.passenger.id){
          passengerFailed = true
        }
      })
      if(!passengerFailed){
        System.out.println("Security Station in line " + lineID + " is sending passenger " + x.passenger.id + " to jail because their bag scan failed.")
        jail ! x.passenger
      }
    }

    case x: BODYFAIL => {
      System.out.println("Security Station in line " + lineID + " received passenger " + x.passenger.id + " who failed their scan.")
      failPassenger.add(x.passenger)
      var bagFailed = false
      failBagLine.forEach( bag => {
        if(bag.passenger.id == x.passenger.id){
          bagFailed = true
        }
      })
      if(!bagFailed){
        System.out.println("Security Station in line " + lineID + " is sending passenger " + x.passenger.id + " to jail because their body scan failed.")
        jail ! x.passenger
      }
    }

    case x: String => println("security station -> " + x);
    case SHUTDOWN => println("Shutting down security station")
  }
}
