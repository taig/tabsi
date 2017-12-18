package io.taig.tabsi

sealed trait Alignment extends Product with Serializable {
  def align(value: String, padding: Int): String
}

object Alignment {
  case object Center extends Alignment {
    override def align(value: String, padding: Int): String = {
      val left = math.floor(padding / 2d).toInt
      val right = math.ceil(padding / 2d).toInt
      " " * left + value + " " * right
    }
  }

  case object Left extends Alignment {
    override def align(value: String, padding: Int): String =
      value + " " * padding
  }

  case object Right extends Alignment {
    override def align(value: String, padding: Int): String =
      " " * padding + value
  }
}
