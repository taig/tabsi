package io.taig.tabsi

sealed trait Ellipsize extends Product with Serializable {
  def truncate(value: String, padding: Int): String
}

object Ellipsize {
  val Character: Char = '…'

  case object End extends Ellipsize {
    override def truncate(value: String, padding: Int): String =
      value.substring(0, value.length - padding) + Character
  }

  case object Middle extends Ellipsize {
    override def truncate(value: String, padding: Int): String = {
      val center = value.length / 2f
      val offset = padding / 2f
      val left = (center - offset).toInt
      val right = (center + offset).toInt
      value.substring(0, left) + Character + value.substring(right)
    }
  }

  case object Start extends Ellipsize {
    override def truncate(value: String, padding: Int): String =
      Character + value.substring(padding)
  }
}
