package io.taig.tabsi

case class Styles(values: List[Style]) extends AnyVal

object Styles {
  val Empty = Styles(List.empty)

  def apply(style: Style, styles: Style*): Styles = Styles(style +: styles.toList)
}