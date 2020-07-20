package datastructures

// A Custom Set datastructure for our new Rational class
// Set trait extends SetOps, Equals and IterableFactoryDefaults 
// Ref: https://www.scala-lang.org/api/current/scala/collection/Set.html
class MySet private(val len: Int, values: List[MyRational]) extends Set[MyRational]{
  
  def distinct(list:List[MyRational]):List[MyRational] =
    list.foldLeft(List[MyRational]()) {
      case (acc, item) if acc.exists(e => {
        e.numerator == item.numerator && e.denomerator == item.denomerator
      }) => acc
      case (acc, item) => item::acc
    }
  
  def elems = distinct(values)
  

  def this(elems: List[MyRational]) =
    this(elems.length, elems)
  
  override def incl(elem: MyRational): Set[MyRational] = {
    return new MySet(elem :: elems)
  }

  override def excl(elem: MyRational): Set[MyRational] = {
    return new MySet(elems.filter( r => r != elem))
  }

  override def contains(elem: MyRational): Boolean = {
    val bool = elems.exists(e => {
      e.numerator == elem.numerator && e.denomerator == elem.denomerator
    })
    bool
  }

  override def iterator: Iterator[MyRational] = {
    class SetIterator(elems:  List[MyRational]) extends Iterable[MyRational] {
      var currIndex = 0
      override def iterator: Iterator[MyRational] = new Iterator[MyRational] {
        def hasNext = (elems.length ) > currIndex
        def next = {
          val prevIndex = currIndex
          currIndex = currIndex + 1
          elems(prevIndex)
        }
      }
    }
    return new SetIterator(elems).iterator
  }
}
