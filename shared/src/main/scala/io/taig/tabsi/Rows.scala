package io.taig.tabsi

import cats.data.NonEmptyList

case class Rows[A](values: NonEmptyList[Row[A]]) extends AnyVal {
  def width: Int = values.head.width

  def height: Int = values.length

  def combine(rows: Rows[A]): Rows[A] = Rows(values concatNel rows.values)

  def toColumns: Columns[A] = {
    val columns = values.head.values.map(Column(_))
    val result = values.tail.foldLeft(columns) {
      case (columns, row) =>
        (columns zipWith row.values) { (column, cell) =>
          Column(column.values concat List(cell))
        }
    }
    
    Columns(result)
  }
}