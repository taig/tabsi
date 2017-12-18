package io.taig.tabsi

case class Styles(values: Seq[Style])

object Styles {
  val Empty = Styles(Seq.empty)

  def apply(style: Style, styles: Style*): Styles = Styles(style +: styles)
}