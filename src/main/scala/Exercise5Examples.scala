import exercise5.Exercise5

object Exercise5Examples {
  def main(args: Array[String]): Unit = {
    val e5 = new Exercise5()
    val fibVals = List(0,1,2,3,10,20)
    fibVals.foreach(x => println(e5.fib(x)))
  }
}
