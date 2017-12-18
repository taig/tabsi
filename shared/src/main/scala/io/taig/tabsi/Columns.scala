package io.taig.tabsi

case class Columns[A](values: Seq[Column[A]]) extends AnyVal {
  /** Height as the amount of [[Rows]] */
  def height: Int = values.map( _.height ).min

  /** Width as the amount of [[Columns]] */
  def width: Int = values.length

  def appendRight(columns: Columns[A]): Columns[A] =
    Columns(values ++ columns.values)

  def prependLeft(columns: Columns[A]): Columns[A] =
    Columns(columns.values ++ values)

  def mapCells[B](f: A => B): Columns[B] = Columns(values.map(_.map(f)))

  /** Convert these [[Columns]] to a [[Rows]] representation */
  def toRows: Rows[A] = {
    val rows = values.foldLeft(Seq.fill(height)(Row.empty[A])) {
      case (rows, Column(cells)) =>
        (rows zip cells).map {
          case (Row(cells), cell) => Row(cells :+ cell)
        }
    }

    Rows(rows)
  }

  override def toString: String = s"Columns(${values.mkString(", ")})"
}

object Columns {
  def empty[A]: Columns[A] = Columns(Seq.empty)

  def apply[A](column: Column[A], columns: Column[A]*): Columns[A] =
    Columns(column +: columns)
}