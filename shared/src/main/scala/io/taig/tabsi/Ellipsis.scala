package io.taig.tabsi

sealed trait Ellipsis extends Product with Serializable

object Ellipsis {
  case object End extends Ellipsis 

  case object Middle extends Ellipsis 

  case object Start extends Ellipsis
}