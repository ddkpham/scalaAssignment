import nqueens.NQueens

// Link to solutions for confirmation. https://en.wikipedia.org/wiki/Eight_queens_puzzle
// Prints out a small table of the number of solutions to NQueen problem. 

object QueenExamples {
  def main(args: Array[String]): Unit = {
    // 1. Prints out a small table of the number of solutions to NQueen problem.
    printTable() // may take a second. 
    
    // 2. You can also look at the solutions for a specific n. Ex. 
    println(NQueens.nQueensList(5))
    
    // 3. You can also check if a solution is valid. 
    println(NQueens.validSolution(List(4, 2, 5, 3, 1)))
    println(NQueens.validSolution(List(4, 1, 5, 3, 2)))
    
  }
  
  def printTable() = {
    println("n    |     number of solutions    ")
    List.range(1,11).foreach(n => {
      // formatting 
      if(n == 10){
        println(s"$n   |             ${NQueens.nQueensList(n).length}")
      } else {
        println(s"$n    |             ${NQueens.nQueensList(n).length}")
      }
    })
  }
}
