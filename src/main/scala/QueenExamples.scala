import nqueens.NQueens
object QueenExamples {
  def main(args: Array[String]): Unit = {
    println(NQueens.nQueensList(10).length)
    //println(NQueens.validSolution(List(8, 4, 3, 7, 2, 6, 1, 5)))
  }
}
