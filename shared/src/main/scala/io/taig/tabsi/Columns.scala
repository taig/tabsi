package io.taig.tabsi

import cats.data.NonEmptyList

case class Columns[A](values: NonEmptyList[Column[A]]) extends AnyVal {
  def width: Int = values.length

  def height: Int = values.head.height

  def combine(table: Columns[A]): Columns[A] = Columns(values concatNel table.values)

  def toRows: Rows[A] = {
    val rows = values.head.values.map(Row(_))
    val result = values.tail.foldLeft(rows) {
      case (rows, column) =>
        (rows zipWith column.values) { (row, cell) =>
          Row(row.values concat List(cell))
        }
    }

    Rows(result)
  }
}