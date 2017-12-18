package io.taig.tabsi

case class Rows[A](values: Seq[Row[A]]) extends AnyVal {
  /** Height as the amount of [[Rows]] */
  def height: Int = values.length

  /** Width as the amount of [[Columns]] */
  def width: Int = values.map( _.width ).min

  def appendBottom(rows: Rows[A]): Rows[A] = Rows(values ++ rows.values)

  def prependTop(rows: Rows[A]): Rows[A] = Rows(rows.values ++ values)

  def mapCells[B](f: A => B): Rows[B] = Rows(values.map(_.map(f)))

  /** Convert these [[Rows]] to a [[Columns]] representation */
  def toColumns: Columns[A] = {
    val columns = values.foldLeft(Seq.fill(width)(Column.empty[A])) {
      case (columns, Row(cells)) =>
        (columns zip cells ).map {
          case (Column(cells), cell) => Column(cells :+ cell)
        }
    }

    Columns(columns)
  }
}

object Rows {
  def empty[A]: Rows[A] = Rows(Seq.empty)
}