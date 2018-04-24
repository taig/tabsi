package io.taig.tabsi

import cats.data.NonEmptyList

case class Column[A](values: NonEmptyList[A]) extends AnyVal {
  def height: Int = values.length

  def combine(column: Column[A]): Columns[A] =
    Columns(NonEmptyList.of(this, column))
}

object Column {
  def apply[A](cell: A, cells: A*): Column[A] =
    Column(NonEmptyList(cell, cells.toList))
}