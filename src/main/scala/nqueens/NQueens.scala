package nqueens

//     N queens problem
//     The objective is to place N queens on an NxN board such that 
//     there are  no two queens are attacking each other. This means 
//     no two queens are in the same row, the same column, or on the same diagonal.
//
//     The positions of the queens as a list of numbers 1..N. The indices correspond 
//     to the position of the ith column queen and the value of the index is the queen row. 
//     Example: For 8 queens problem List(4, 2, 7, 3, 6, 8, 5, 1) means that the queen in the first
//     column is in row 4, the queen in the second column is in row 2, etc.  Luckily, 
//     the solution to the problem is fairly simple. Going through each column one at a time, 
//     we need to check that there are no horizontal conflicting queens and that there are no 
//     immediate queens to the upper right and lower right. 

object NQueens {
  def remove(num: Int, list: List[Int]) = list diff List(num)
  
  def nQueensList(n: Int) :  List[List[Int]] = {
    def generateSolutions(currQueens: List[Int], remainingQueens: List[Int]): List[List[Int]] =
      if (currQueens.length == n && validSolution(currQueens) ) List(currQueens)
      else remainingQueens.flatMap(c => generateSolutions(c :: currQueens, remove(c, remainingQueens)))
    generateSolutions(Nil, List.range(1, n+1))
  }
  
  @scala.annotation.tailrec
  def diagonalCheck(currQueens: List[Int], upperRight: Int, lowerRight: Int): Boolean = {
    currQueens match {
      case Nil => true // last queen 
      case q :: qs => q != upperRight && q != lowerRight && diagonalCheck(qs, upperRight + 1, lowerRight - 1)
    }
  }
  
  @scala.annotation.tailrec
  def validSolution(queens: List[Int]): Boolean = {
    queens match {
      case Nil => true // base case
      case q :: tail => horizontalCheck(queens) && diagonalCheck(tail, q + 1, q - 1) && validSolution(tail)
    }
  }
  
  def horizontalCheck(currQueens: List[Int]): Boolean ={
    val queenSet = currQueens.toSet
    currQueens.length == queenSet.size
  }
  
  def verticalCheck(currQueens: List[Int]): Boolean = {
    return true // impossible to place two queens vertically since we are using indices as queen column placement. 
  }
}