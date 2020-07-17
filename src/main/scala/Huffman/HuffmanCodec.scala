package Huffman
import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}

//Dyanmic huffman encoding is an adaptive coding technique that is based on 
//Huffman Coding. It assumes no prior knowledge of the transmitted message to 
//built it's encoding scheme. It is a variable length encoding technique for compression.'
//The benefit of being dynamic allows source messages to be encoded in real time. 
class HuffmanTree(sourceSymbol: Char, symbolCount: Int) {
  var left: HuffmanTree = _
  var right: HuffmanTree = _
  var parent: HuffmanTree = _
  var symbol = sourceSymbol
  var count = symbolCount
  
  def compare(that: HuffmanTree): Int = that.count.compareTo(this.count)
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
    var symbolFreqMap: Map[Char,Int] = generateFrequencyMap(message)
    println(symbolFreqMap)
    
    // create a huffman node for each symbol and add to worklist 
    val workList = new mutable.PriorityQueue[HuffmanTree]()(Ordering.by(ht => ht.count))
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
      val newHuffmanNode = new HuffmanTree('$', left.count + right.count)
      // arrange tree 
      newHuffmanNode.left = left;
      newHuffmanNode.right = right;
      left.parent = newHuffmanNode;
      right.parent = newHuffmanNode
      workList.enqueue(newHuffmanNode)
    }
    
    var encodedMessage = encoder(symbolList)
    return encodedMessage
  }
  
  def encoder(symbolList: ListBuffer[HuffmanTree]): String ={
    var encodedMessage = new mutable.StringBuilder()
    for(i <- 0 to symbolList.length-1){
      var node = symbolList(i)
      var currNode: HuffmanTree = node;
      var prevNode:HuffmanTree = node;
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

    val codeword = encodedMessage.toString()
    return codeword
  }
  
  def generateFrequencyMap(message: String) : mutable.Map[Char,Int] = {
    var messageArr = message.toList
    var frequencyMap = Map[Char,Int]()
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
    val encodedMessegeLen = encodedMessage.length
    return messageLen.toFloat/encodedMessegeLen
  }
}


