package datastructures

// This class was constructed to demonstrate how easy it is to build a rich API for a 
// class by leveraging traits. Once a class implements a Trait's abstract method, 
// it has access to every concrete method in the Trait. Concrete methods in the 
// trait are implemented via abstract method. Here we implement Ordered and Numeric,
// This permits MyRational to be treated as a numeric number with ordering. 
class MyRational(num: Int, denom: Int) extends Ordered[MyRational] with Numeric[MyRational]{
  require(denom != 0, "Denominator cannot be 0")
  private def gcd(x:Int, y:Int): Int = if (y == 0) x.abs else gcd(y, x % y)
  private val gFactor = gcd(num, denom)
  def numerator = if(denom < 0) (-num)/gFactor else num/gFactor
  def denomerator: Int = if(denom < 0) (-denom)/gFactor else denom/gFactor
  
  // by implementing abstract method compare on Trait Ordered, MyRational class
  // now has the following methods [ <, <= , > , >= , compareTo ] 
  def compare(that: MyRational) = {
     this.numerator * that.denomerator - that.numerator * this.denomerator
  }
  
  // operators are just methods on a class. Lets implement them. Handy trait 
  // seems to be the Numerical Trait. 
  override def plus(x: MyRational, y: MyRational): MyRational = {
    val num = x.numerator * y.denomerator + y.numerator * x.denomerator
    val denom = x.denomerator * y.denomerator 
    return new MyRational(num, denom)
  }

  override def minus(x: MyRational, y: MyRational): MyRational = {
    val num = x.numerator * y.denomerator - y.numerator * x.denomerator
    val denom = x.denomerator * y.denomerator
    return new MyRational(num, denom)
  }

  override def times(x: MyRational, y: MyRational): MyRational = {
    return new MyRational(x.numerator * y.numerator, x.denomerator * y.denomerator)
  }

  override def negate(x: MyRational): MyRational = {
    return new MyRational(-x.numerator, x.denomerator)
  }

  override def fromInt(x: Int): MyRational = {
    return new MyRational(x, 1)
  }

  override def parseString(str: String): Option[MyRational] = {
    try {
      val strArr = str.split("/")
      if (strArr.length == 2) {
        val rational = new MyRational(strArr(0).toInt, strArr(1).toInt)
        return Some(rational)
      } else {
        return None
      }
    } catch {
      case e : NumberFormatException => None
    }
  }

  override def toInt(x: MyRational): Int = {
    return x.numerator / x.denomerator
  }

  override def toLong(x: MyRational): Long = {
    return x.numerator.toLong / x.denomerator.toLong
  }

  override def toFloat(x: MyRational): Float = {
    return x.numerator.toFloat / x.denomerator.toFloat
  }

  override def toDouble(x: MyRational): Double = {
    return x.numerator.toDouble / x.denomerator.toDouble
  }

  override def compare(x: MyRational, y: MyRational): Int = {
    return x compare y
  }

  override def toString = s"$numerator/$denomerator"
  
  // We can also implement arithmetic operators using our new methods
  def +(that: MyRational) = {
    this.plus(this, that)
  }

  def -(that: MyRational) = {
    this.minus(this, that)
  }

  def *(that: MyRational) = {
    this.times(this, that)
  }

  def /(that: MyRational) = {
    this.times(this, new MyRational(that.denomerator, that.numerator))
  }
  
   def == (that: MyRational) = {
    this.numerator == that.numerator && this.denomerator == that.denomerator
  }
}
