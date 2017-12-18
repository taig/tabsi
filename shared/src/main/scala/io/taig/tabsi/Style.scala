package io.taig.tabsi

case class Style(alignment: Alignment, width: Width)

object Style {
  val Default: Style = Style(Alignment.Left, Width.Auto)
}
