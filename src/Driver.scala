import java.util.Scanner

import akka.actor.{Actor, ActorRef, ActorSystem, PoisonPill, Props}

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
  val usage = """
   Usage: driver [max-passengers num] [max-Lines num]
              """
  var MAX_LINES: Int = 0
  var MAX_PASSENGERS: Int = 0


  def main(args: Array[String]): Unit = {

    val scanner = new Scanner(System.in)
    println("Welcome to our TSA Security Screen Simulator")
    println()

    print("Enter number of Passengers: ")
    MAX_PASSENGERS = scanner.nextInt()

    println()

    print("Enter number of Lines: ")
    MAX_LINES = scanner.nextInt()

    println("Running simulation..... \n")


    // set up our ActorSystem
    val system = ActorSystem.create()

    // There is only one jail
    val jail = system.actorOf(Props(new Jail))

    //create a "line" which consists of a queue, bag scan, body scan, and security
    val allLines = new ArrayBuffer[ActorRef]
    for( i <- 0 to MAX_LINES) {

      val id = i + 1
      // Creates the actors that make up a line
      val security = system.actorOf(Props(new SecurityStation(jail, id)))
      val bodyScanActor = system.actorOf(Props(new BodyScan(security, id)))
      val baggageActor = system.actorOf(Props(new BaggageScan(security, id)))
      val queue = system.actorOf(Props(new Queue(baggageActor,bodyScanActor, id)))

      allLines += system.actorOf(Props( new Line(id,queue,baggageActor,bodyScanActor,security)  ))
    }

    // create and populate circular design
    val circularIterator = new CircularIterator[ActorRef](allLines)

    // create all passengers
    val allPassengers = new ArrayBuffer[PASSENGER]
    for( i <- 0 to MAX_PASSENGERS ) allPassengers += new PASSENGER(i+1)

    // check the passengers documents and collect all the ones that pass
    val approvedPassengers = allPassengers.collect( documentCheck() )


    for(passenger <- approvedPassengers){
      if(passenger != null)
        circularIterator.next() ! passenger
    }

    // sends shutdown message to all lines
    for(line <- allLines){
        line ! PoisonPill
    }

  }




  /**
    * This method simulates the an inspection which the passenger fails 20%
    * of the time
    *
    * The method gets passed an Passenger and if the randomly generated number
    * passes our test, then the same Passenger is returned and added to the
    * collection
    *
    * Generates a random integer between 0 (inclusive) and 100(exclusive)
    * so the real range is 0-99, which includes 100 integers.
    *
    * @return returns false 20% of the time
    */
  def documentCheck(): PartialFunction[PASSENGER,PASSENGER] ={
    case x if Random.nextInt(100) > 20 => x
    case default => println("passenger " + default.id +" rejected"); null; // default case
  }


  /**
    * This class takes an ArrayBuffer and can iterate indefinltey through the passed
    * in ArrayBuffer
    *
    * @param buffer
    * @tparam T
    */
  class CircularIterator[T](buffer: ArrayBuffer[T]){
    var idx= -1 // so that we start at index 0 when we call the next() function
    var size = buffer.size
    def next()={
      idx = idx + 1
      buffer(idx % size)
    }
  }




}

