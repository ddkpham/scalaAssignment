import Huffman.HuffmanCodec

object HuffmanExamples {
  def main(args: Array[String]): Unit = {
    val huffmanCodec = new HuffmanCodec()
    val message = "Hello darkness, my old friend"
    val encodedMessage = huffmanCodec.encode(message)
    val compressionRatio = huffmanCodec.compressionRatio(message)
    println(s"The encoded message is $encodedMessage")
    println(s"The compression ratio was $compressionRatio")
  }
}
