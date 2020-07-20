import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ProducerConsumer extends App{
  case class Start(start: Int, end: Int, queue: ActorRef, consumers: IndexedSeq[ActorRef])
  case class PackagedCandy(n: Int, name: String, consumers: IndexedSeq[ActorRef])
  case class Candy(n: Int, name: String)
  case class NoMoreBonBon(message: String)
  val totalBonBons = 20
  
  class ProducerActor extends Actor {
    val candies = List("Caramel", "Chocolate", "Lollipop", "Sucker")
    val randGenerator = scala.util.Random
    def receive = {
      case Start(start, end, queue, consumers) => {
        List.range(start,end + 1).foreach(
            index => {
              println(s"Candy Made by ${self.path.name}!")
              queue ! PackagedCandy(index, candies(randGenerator.nextInt(4)), consumers)
            }
        )
        
      }
    }
  }
  
  class QueueActor extends Actor {
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
  
  val numProducers = 5
  val numConsumers = 3
  val system = ActorSystem("ProducerConsumer")
  val queue = system.actorOf(Props[QueueActor], "MessageQueue")
  val producerArray = for( i <- 1 to numProducers) yield system.actorOf(Props[ProducerActor], s"Producer$i")
  val consumerArray = for(i <- 1 to numConsumers) yield system.actorOf(Props[ConsumerActor], name = s"Consumer$i")
  
  producerArray.zipWithIndex foreach {
    case(p, i) => {
      if(i == producerArray.length - 1){
        val range = totalBonBons / producerArray.length;
        val start = range * i
        val end = if(totalBonBons % producerArray.length > 0 ) totalBonBons % producerArray.length else (start + range)
        p ! Start(start, end, queue, consumerArray)
      } else {
        val range = totalBonBons / producerArray.length;
        val start = range * i
        p ! Start(start, start + range, queue, consumerArray)
      }
    }
  }
}
