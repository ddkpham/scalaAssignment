package exercise2

class Exercise2 {

  def divisors(n : Int) : List[Int] = {
    for {
      i <- List.range(2, n/2+1)
      if n % i == 0
    } yield i
  }
  
  def primes(n: Int) : List[Int] = {
    for {
      i <- List.range(2, n+1)
      if divisors(i).isEmpty
    } yield  i
  }
  
  def join(sep: String, arr: List[String]): String = {
    val result = new StringBuilder("")
    arr.zipWithIndex.foreach(d => {
      result.append(d._1)
      if(d._2 != arr.length - 1){
        result.append(sep);
      }
    })
    return result.toString();
  }
  def pythagorean(n: Int) : List[(Int, Int, Int)] = {
    for{
      a <- List.range(1, n+1)
      b <- List.range(1, n+1)
      c <- List.range(1, n+1)
      if ((a*a + b*b) == c*c) && a < b
    } yield (a,b,c)
  }
}
