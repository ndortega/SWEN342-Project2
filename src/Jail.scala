import java.util

import akka.actor.Actor


/**
  * The jail knows how many security stations feed it passengers as prisoners.
  * The jail will hold all prisoners until the end of the day when all prisoners in holding are sent to permanent detention.
  */
class Jail extends Actor{
  val jailedPassengers = new util.LinkedList[PASSENGER]()
  val totalLines = Driver.MAX_LINES
  var completed = 0
  var isDone = false

  override def receive = {
    case x: PASSENGER => {
      System.out.println("Passenger " + x.id + " is entering jail.")
      jailedPassengers.add(x)
    }

    case COMPLETED => {
      if(completed == totalLines && !isDone) { // boolean flag ensures we shut down the system once
        isDone = true
        sendToPrison()
        context.system.terminate()
      }
      else
        completed += 1
    }
  }

  def sendToPrison() = {
    jailedPassengers.forEach(passenger=> {
      println("Passenger " + passenger.id + " is being sent to permanent detention.")
    })
    println("Shutting down Jail")

  }
}
