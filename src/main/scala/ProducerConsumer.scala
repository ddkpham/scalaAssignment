import akka.actor.{Actor, ActorRef, ActorSystem, Props}

object ProducerConsumer extends App{
  case class Start(start: Int, queue: ActorRef, consumers: List[ActorRef])
  case class PackagedCandy(n: Int, name: String, consumers: List[ActorRef])
  case class Candy(n: Int, name: String)
  case class NoMoreBonBon(message: String)
  val totalBonBons = 20
  
  class ProducerActor extends Actor {
    val candies = List("Caramel", "Chocolate", "Lollipop", "Sucker")
    val randGenerator = scala.util.Random
    def receive = {
      case Start(start, queue, consumers) => {
        List.range(start,start + 10).foreach(
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
  
  val system = ActorSystem("ProducerConsumer")
  val producer1 = system.actorOf(Props[ProducerActor], "Producer1")
  val producer2 = system.actorOf(Props[ProducerActor], "Producer2")
  val queue = system.actorOf(Props[QueueActor], "MessageQueue")
  val consumer1 = system.actorOf(Props[ConsumerActor], "Consumer1")
  val consumer2 = system.actorOf(Props[ConsumerActor], "Consumer2")
  val consumerArray = List(consumer1, consumer2)
  
  producer1 ! Start(0, queue, consumerArray)
  producer2 ! Start(totalBonBons/2, queue, consumerArray)
}
