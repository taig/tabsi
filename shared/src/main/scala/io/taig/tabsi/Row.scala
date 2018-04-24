package io.taig.tabsi

import cats.data.NonEmptyList

case class Row[A](values: NonEmptyList[A]) extends AnyVal  {
  def width: Int = values.length

  def combine(row: Row[A]): Rows[A] = Rows(NonEmptyList.of(this, row))
}

object Row {
  def apply[A](cell: A, cells: A*): Row[A] =
    Row(NonEmptyList(cell, cells.toList))
}