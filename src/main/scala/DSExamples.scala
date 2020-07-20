import datastructures.MyRational
import datastructures.MySet

// Examples to show how easy it is to implement datastructures with a rich API and minimal effort
// using traits. None of the called methods below will be implemented directory.
object DSExamples {
  def main(args: Array[String]): Unit = {
    val half = new MyRational(1,-2)
    val third = new MyRational(-1,3)
    // simple print
    println(half)
    println(third)
    println("-" * 100)
    
    // operators implemented as methods on MyRational using trait concrete methods
    println(half + third)
    println(half - third)
    println(half * third)
    println(half / third)
    println("-" * 100)
    
    // Ordered Trait concrete methods gained by implementing abstract method. 
    println(half > third)
    println(half < third)
    println(half >= third)
    println(half <= third)
    println(half == new MyRational(1,-2))
    println("-" * 100)
    
    // Numeric Trait concrete methods gained by implementing abstract method. 
    println(half.abs(new MyRational(-20,2)))
    println(half.equiv(half, third))
    println(half.gt(half, third))
    println(half.lt(half, third))
    println(half.lteq(half, third))
    println(half.max(half,third))
    println(half.min(half,third))
    println(half.one)
    println(half.reverse)
    println(half.reversed())
    println(half.sign(half))
    println("-" * 100)
    // etc ... you get the point. full list here: https://www.scala-lang.org/api/current/scala/math/Numeric.html
    
    // Set Datasture that takes only our Rationals
    val myRatSet1 = new MySet(List(half, third, new MyRational(1,4), new MyRational(1,-3)))
    val myRatSet2 = new MySet(List(half, third, new MyRational(1,6), new MyRational(1,-5)))
    println(myRatSet1)
    println(myRatSet2)
    println(myRatSet1 intersect myRatSet2)
    println(myRatSet1 diff myRatSet2)
    println(myRatSet1 ++ myRatSet2)
    println(myRatSet1.dropWhile( r => r.numerator < 0))
    // full List here: https://www.scala-lang.org/api/current/scala/collection/Set.html
  }
}
