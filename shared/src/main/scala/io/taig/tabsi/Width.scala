package io.taig.tabsi

sealed trait Width extends Product with Serializable

object Width {
  case object Auto extends Width
  case class Limit(value: Int, ellipsize: Option[Ellipsize]) extends Width
  case class Fixed(value: Int, ellipsize: Option[Ellipsize]) extends Width
}
