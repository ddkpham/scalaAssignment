import exercise2.Exercise2


object Exercise2Examples {
  def main(args: Array[String]): Unit = {
    // Divisors 
    println("Divisors--------------------------------------")
    val e2 = new Exercise2()
    val divisorTestVals = List(30,64,127)
    divisorTestVals.foreach( x => {println(e2.divisors(x))})
    
    // Primes 
    println("Prime------------------------------------------")
    val primesTestVals =  List(7,100)
    primesTestVals.foreach(x => println(e2.primes(x)))
    
    // Join
    println("Join--------------------------------------------")
    val params = List(
      (", " , List("one","two","three")),
      ("+",  List("1","2","3")),
      ("X", List("abc")),
      ("X", List[String]())
    )
    params.foreach(x => println(e2.join(x._1, x._2)))
    
    // Pythagorean Triples 
    println("Pythagorean Triples------------------------------")
    val pythagoreanNums = List(10,30)
    pythagoreanNums.foreach(x => println(e2.pythagorean(x)))
  }
  
}
