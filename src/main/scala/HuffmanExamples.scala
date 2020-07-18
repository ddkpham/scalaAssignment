import huffman.HuffmanCodec

object HuffmanExamples {
  def main(args: Array[String]): Unit = {
    val huffmanCodec = new HuffmanCodec()
    // Message has too much variance in characters to leverage frequency. Entropy of message is too high. 
    val badMessage = "the quick brown fox jumped over the lazy dog."
    val encodedMessage = huffmanCodec.encode(badMessage)
    val compressionRatio = huffmanCodec.compressionRatio(badMessage)
    println(s"The encoded message is $encodedMessage")
    println(s"The compression ratio was $compressionRatio")
    
    
    // This Message uses a small set of symbols. Should be easier to encode with our frequency scheme. 
    println("-" * 200)
    val goodMessage = "ally is an ally for all allistars, allisons, and als."
    val encodedMessage2 = huffmanCodec.encode(goodMessage)
    val compressionRatio2 = huffmanCodec.compressionRatio(goodMessage)
    println(s"The encoded message is $encodedMessage2")
    println(s"The compression ratio was $compressionRatio2")
    
    
    // Message that is really easy to encode 
    println("-" * 200)
    val trivialMessage = "hahahahahahahahahahahaha no way hahahahahahahahahahahahaahahahahhahahaha"
    val encodedMessage3 = huffmanCodec.encode(trivialMessage)
    val compressionRatio3 = huffmanCodec.compressionRatio(trivialMessage)
    println(s"The encoded message is $encodedMessage3")
    println(s"The compression ratio was $compressionRatio3")
  }
}
