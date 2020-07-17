import exercise4.Exercise4

object Exercise4Examples {
  def main(args: Array[String]): Unit = {
    
      // hailSeq
      println("hailSeq--------------------------------------")
      val e4 = new Exercise4()
      val hailSeqVals = List(1,11,6)
      hailSeqVals.foreach(x => println(e4.hailSeq(x)))
    
      // mergeSort 
      println("mergeSort--------------------------------------")
      val mergeSortIntVals = List(
        List(1,9,3,2,7,6,4,8,5),
        List(6,2,4,8,9,5,3,1,7,10),
        List(),
        List(4)
      )
      mergeSortIntVals.foreach(list => println(e4.mergeSort(list)(Ordering[Int])))
    
      val string = "The quick brown fox jumps over the lazy dog."
      val stringArr = string.toList
      println(e4.mergeSort(stringArr)(Ordering[Char]))
      
    
  }
}
