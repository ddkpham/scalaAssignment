package exercise4
import math.Ordering

class Exercise4 {
  def hailstone(n: Int): Int = {
    return if (n % 2 == 0) n / 2 else 3 * n + 1
  }

  def hailSeq(n: Int): List[Int] = {
    n match {
      case 1 => List(1)
      case _ => n :: hailSeq(hailstone(n))
    }
  }
  
  def firstHalf[T](value: List[T]) : List[T] = {
    return value.take(value.length / 2)
  }
  
  def secondHalf[T](value: List[T]) : List[T] = {
    return value.drop(value.length/2)
  }
  
  def mergeSort[T](ls: List[T])(order: Ordering[T]): List[T] = {
    def merge(xlist: List[T], ylist: List[T]): List[T] = (xlist, ylist) match {
      case (Nil, _) => ylist
      case (_, Nil) => xlist
      case (x :: xs, y :: ys) =>
        if (order.lt(x, y))
          x :: merge(xs, y::ys)
        else
          y :: merge(x::xs, ys)
    }
    
    ls match {
      case Nil => List()
      case l::Nil => List(l)
      case ls => merge (mergeSort(firstHalf(ls))(order), mergeSort(secondHalf(ls))(order))  
    }
  }
  
}
