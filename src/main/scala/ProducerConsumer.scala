import akka.actor.{Actor, ActorRef, ActorSystem, Props}

// Implemting classic producer and consumer problem using actors model 
// Everything in the actor model should be an actor. We will use a 
// queue actor instead of implementing a shared queue of work. 
// This model is flexible enough to add more queues to the system, however, 
// that does not follow closely to original consumer producer problem. 
object ProducerConsumer extends App{
  // Here we are defining messages for our actors. 
  case class Start(start: Int, end: Int, queue: ActorRef, consumers: IndexedSeq[ActorRef])
  case class PackagedCandy(n: Int, name: String, consumers: IndexedSeq[ActorRef])
  case class Candy(n: Int, name: String)
  case class NoMoreBonBon(message: String)
  // feel free to adjust number of total bon bons 
  val totalBonBons = 20
  
  class ProducerActor extends Actor {
    val candies = List("Caramel", "Chocolate", "Lollipop", "Sucker")
    val randGenerator = scala.util.Random
    // Each producer will take up a portion of the work required to produce total candies
    def receive = {
      case Start(start, end, queue, consumers) => {
        List.range(start,end).foreach(
            index => {
              println(s"Candy Made by ${self.path.name}!")
              queue ! PackagedCandy(index, candies(randGenerator.nextInt(4)), consumers)
            }
        )
        
      }
    }
  }
  
  class QueueActor extends Actor {
    // Instead of implementing a shared Queue, lets use a Queue actor that unpackages the wrapped candy 
    // and forwards it along to a consumer randomly. Load balancing will be done with randomization. 
    val randGenerator = scala.util.Random
    def receive = {
      case PackagedCandy(num, name, consumers) =>
        val luckyCustomer = randGenerator.nextInt(consumers.length)
        println(s"Sending Candy to Consumer${luckyCustomer + 1}")
        consumers(luckyCustomer) ! Candy(num, name)
    }
    
  }
  
  class ConsumerActor extends Actor {
    def receive = {
      case Candy(num, name) =>
       val consumer = self.path.name
       println(s"Yum! $consumer just ate candy ${num} a $name")
        
        if(num == totalBonBons - 1){
          context.system.terminate()
        }
    }
  }
  // Feel free to adjust num of producers and consumers. 
  val numProducers = 6
  val numConsumers = 7
  val system = ActorSystem("ProducerConsumer")
  val queue = system.actorOf(Props[QueueActor], "MessageQueue")
  val producerArray = for( i <- 1 to numProducers) yield system.actorOf(Props[ProducerActor], s"Producer$i")
  val consumerArray = for(i <- 1 to numConsumers) yield system.actorOf(Props[ConsumerActor], name = s"Consumer$i")
  
  // Divy up work to producers taking careful consideration of evenly splitting up work. 
  // Calculate the amount of work that each producer should do. If there is an uneven amount of work, 
  // let the last producer handle that edge case. 
  producerArray.zipWithIndex foreach {
    case(p, i) => {
      if(i == producerArray.length - 1){ 
        val range = math.ceil(totalBonBons.toFloat / producerArray.length).toInt;
        val start = range * i
        val end = if(totalBonBons % producerArray.length > 0 ) start + (totalBonBons % range) else (start + range)
        p ! Start(start, end, queue, consumerArray)
      } else {
        val range = math.ceil(totalBonBons.toFloat / producerArray.length).toInt;
        val start = range * i
        p ! Start(start, start + range, queue, consumerArray)
      }
    }
  }
}
