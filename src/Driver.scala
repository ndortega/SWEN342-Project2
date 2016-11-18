import akka.actor.{Actor, ActorRef, ActorSystem, Props}

import scala.collection.mutable.ArrayBuffer
import scala.util.Random


/**
  * At the beginning of the day, the system will initialize and turn on all of the scanners.
  *
  * At the end of the day, the system closes by turning off all the scanners, and sending prisoners
  * in jail to permanent detention facilities. As passengers and their bags clear through the system,
  * it will shutdown, i.e. the queue must be empty and the body scanner must be ready before the body
  * scanner is turned off.
  */
object Driver {
  val MAX_PASSENGERS = 20
  val MAX_LINES = 10

  def main(args: Array[String]): Unit = {

    // set up our ActorSystem
    val system = ActorSystem.create()

    // There is only one jail
    val jail = system.actorOf(Props(new Jail))

    //create a "line" which consists of a queue, bag scan, body scan, and security
    val allLines = new ArrayBuffer[ActorRef]
    for( i <- 0 to MAX_LINES) {

      // Creates the actors that make up a line
      val bodyScanActor = system.actorOf(Props(new BodyScan))
      val baggageActor = system.actorOf(Props(new BaggageScan))
      val queue = system.actorOf(Props(new Queue))
      val security = system.actorOf(Props(new SecurityStation))

      allLines += system.actorOf(Props( new Line(i,queue,baggageActor,bodyScanActor,security,jail)  ))
    }



    // create all passengers
    val allPassengers = new ArrayBuffer[ActorRef]
    for( i <- 0 to MAX_PASSENGERS ) allPassengers += system.actorOf(Props(new Passenger))

    // check the passengers documents and collect all the ones that pass
    val approvedPassengers = allPassengers.collect( documentCheck() )



    // shuts down the actor system
    system.terminate()
  }


  /**
    * This method simulates the an inspection which the passenger fails 20%
    * of the time
    *
    * The method gets passed an ActorRef and if the randomly generated number
    * passes our test, then the same ActorRef is returned and added to the
    * collection
    *
    * Generates a random integer between 0 (inclusive) and 100(exclusive)
    * so the real range is 0-99, which includes 100 integers.
    * @return returns true 80% of the time
    */
  def documentCheck(): PartialFunction[ActorRef,ActorRef] ={
    case x if Random.nextInt(100) > 20 => x
  }




}