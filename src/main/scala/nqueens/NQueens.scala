package nqueens

//     N queens problem
//     The objective is to place N queens on an NxN board such that 
//     there are  no two queens are attacking each other. This means 
//     no two queens are in the same row, the same column, or on the same diagonal.
//
//     The positions of the queens as a list of numbers 1..N. The indices correspond 
//     to the position of the ith column queen and the value of the index is the queen row. 
//     Example: For 8 queens problem List(4, 2, 7, 3, 6, 8, 5, 1) means that the queen in the first
//     column is in row 4, the queen in the second column is in row 2, etc.  

object NQueens {
  def nQueensList(n: Int) :  List[List[Int]] = {
    def generatePossibleBoards(currQueens: List[Int], remainingCols: Set[Int]): List[List[Int]] = {
      if (currQueens.length == n) List(currQueens)
      else remainingCols.toList.flatMap(c => generatePossibleBoards(c :: currQueens, remainingCols - c))
    }

    val workList = generatePossibleBoards(Nil, Set() ++ (1 to n))
    val solutions = workList.filter(board => validSolution(board))
    return solutions
  }
  
  @scala.annotation.tailrec
  def diagonalCheck(currQueens: List[Int], upperRight: Int, lowerRight: Int): Boolean = {
    currQueens match {
      case Nil => true // last queen 
      case q :: tail => q != upperRight && q != lowerRight && diagonalCheck(tail, upperRight + 1, lowerRight - 1)
    }
  }
  
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
}