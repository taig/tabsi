package io.taig.tabsi

sealed trait Cell extends Product with Serializable {
  def size: Int

  def format(alignment: Alignment,
             width: Int,
             ellipsize: Option[Ellipsize]): String
}

object Cell {
  def apply(value: String): Cell = Value(value)

  case object Empty extends Cell {
    override val size: Int = 0

    override def format(alignment: Alignment,
                        width: Int,
                        ellipsize: Option[Ellipsize]): String = " " * width

    override val toString: String = "Cell.Empty"
  }

  case class Value(value: String) extends Cell {
    override def size: Int = value.length

    override def format(alignment: Alignment,
                        width: Int,
                        ellipsize: Option[Ellipsize]): String =
      if (width == value.length) value
      else if (width > value.length) {
        val padding = width - value.length
        alignment.align(value, padding)
      } else {
        val padding = value.length - width + 1
        ellipsize.fold(value.substring(0, width))(_.truncate(value, padding))
      }

    override def toString: String = s"Cell.Value($value)"
  }
}
