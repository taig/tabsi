package io.taig.tabsi

sealed trait Cell extends Product with Serializable {
  def size: Int
}

object Cell {
  case object Empty extends Cell {
    override val size: Int = 0

    override val toString: String = "Cell.Empty"
  }

  case class Value(value: String) extends Cell {
    override def size: Int = value.length

    override def toString: String = s"Cell.Value($value)"
  }

  def apply(value: String): Cell = Value(value)
}