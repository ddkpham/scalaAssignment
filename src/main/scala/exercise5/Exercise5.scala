package exercise5

class Exercise5 {
  def fib(n: Int): Int = {
    if(n == 0) return 0
    if(n == 1) return 1
    val fibArr = new Array[Int](n+1)
    fibArr(0) = 0
    fibArr(1) = 1
    for( i <- 2 to n)
      fibArr(i) = fibArr(i-1) + fibArr(i-2)
    return fibArr(n)
  }
}
