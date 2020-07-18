package huffman
import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}

//huffman encoding is an  coding technique that is developed by 
//David Huffman. It is a variable length encoding technique for compression.
//It is based on leveraging the symbol frequency of a source languages alphabet. 
class HuffmanTree(val symbol: Char, val count: Int) {
  var left: HuffmanTree = _
  var right: HuffmanTree = _
  var parent: HuffmanTree = _
}
// companion object for class huffman tree. We will use this so we can use a priority queue to build huffman tree
object HuffmanTree {
  // descending order. 
  implicit val ordering : Ordering[HuffmanTree] = Ordering.by(_.count)
  // ascending order. 
  val reverseOrdering : Ordering[HuffmanTree] = ordering.reverse
}

class QElement(symbol: Char, priorityEl: Int){
  var element = symbol;
  var priority = priorityEl;
}

class HuffmanCodec {
  def encode(message: String): String = {
    // generate frequency table for each symbol 
    // This is normally some precomputed frequency map with the calculated probability each 
    // letter appearing from some large corpus of text. For simplicity, I'm going to create
    // a frequency map from the letter counts in the string. 
    var symbolFreqMap: mutable.Map[Char,Int] = generateFrequencyMap(message)
    
    // create a huffman node for each symbol using workList algorithm. 
    val workList = new mutable.PriorityQueue[HuffmanTree]()(HuffmanTree.ordering)
    // symbolList will store a reference to each leaf node in the huffman tree. 
    val symbolList: ListBuffer[HuffmanTree] = new ListBuffer()
    for((k,v) <- symbolFreqMap){
      val huffmanNode = new HuffmanTree(k,v)
      workList.enqueue(huffmanNode)
      symbolList += huffmanNode
    }
    
    // create huffman tree
    while(workList.length > 1){
      val left = workList.dequeue()
      val right = workList.dequeue()
      val metaNode = new HuffmanTree('$', left.count + right.count)
      // arrange tree 
      arrangeMetaTree(metaNode, left, right)
      workList.enqueue(metaNode)
    }
    // tree is complete. Use it to generate encoded message from original symbolList.
    var encodedMessage = encoder(symbolList, message)
    return encodedMessage
  }
  
  def arrangeMetaTree(metaNode:HuffmanTree, left: HuffmanTree, right : HuffmanTree): Unit = {
    metaNode.left = left;
    metaNode.right = right;
    left.parent = metaNode;
    right.parent = metaNode
  }
  
  def encoder(symbolList: ListBuffer[HuffmanTree], message: String): String ={
    var encodedMessage = new mutable.StringBuilder()
    var messageArr = message.toCharArray()
    for(i <- messageArr.indices){
      var symbol = messageArr(i)
      var node: Option[HuffmanTree] = symbolList.find(ht => ht.symbol == symbol)
      node match {
        case Some(huffTree: HuffmanTree) => extendEncodedMessage(encodedMessage, huffTree)
        case None => println("symbol not found")  
      }
    }
    val codeword = encodedMessage.toString()
    return codeword
  }

  def extendEncodedMessage(encodedMessage: mutable.StringBuilder, node: HuffmanTree): Unit ={
    var currNode:  HuffmanTree = node;
    var prevNode:  HuffmanTree = node;
    // move up huffman tree outputing edge values as it goes along.
    while(currNode.parent != null){
      prevNode = currNode
      currNode = prevNode.parent
      if(prevNode == currNode.left){
        encodedMessage.append("0")
      } else if (prevNode == currNode.right){
        encodedMessage.append("1")
      } else {
        println("idiot")
      }
    }
  }
  
  def generateFrequencyMap(message: String) : mutable.Map[Char,Int] = {
    var messageArr = message.toList
    var frequencyMap = mutable.Map[Char,Int]()
    messageArr.foreach(symbol => {
      if (frequencyMap.contains(symbol)) {
        frequencyMap(symbol) = frequencyMap(symbol) + 1
      } else {
        frequencyMap += (symbol -> 1)
      }
    })
    frequencyMap
  }
  
  def compressionRatio(message: String) : Float = {
    val encodedMessage = encode(message);
    val messageLen = message.length * 8 // 8bits / byte
    val encodedMessageLen = encodedMessage.length
    return messageLen.toFloat/encodedMessageLen
  }
}


