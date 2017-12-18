package io.taig.tabsi

sealed trait Divider extends Product with Serializable

object Divider {
  case class Grid(
    horizontal: Horizontal,
    vertical: Vertical,
    intersection: Char ) extends Divider
  
  case class Horizontal(divider: Char ) extends Divider
  
  case class Vertical( divider: Char ) extends Divider

  val Default: Divider = Vertical( ' ' )
}