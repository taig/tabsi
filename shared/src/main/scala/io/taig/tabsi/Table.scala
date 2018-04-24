package io.taig.tabsi

import cats.data.NonEmptyList

object Table {
  def columns[A](column: Column[A], columns: Column[A]*): Columns[A] =
    Columns(NonEmptyList(column, columns.toList))

  def rows[A](row: Row[A], rows: Row[A]*): Rows[A] =
    Rows(NonEmptyList(row, rows.toList))
}
