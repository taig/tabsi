package io.taig.tabsi

trait Printer[F[_]] {
  def print(value: F[String]): String
}

object Printer {
  def apply[F[_]](implicit p: Printer[F]): Printer[F] = p

  implicit def column(implicit p: Printer[Rows]): Printer[Column] = column =>
    p.print(column.toRows)

  implicit def columns(implicit p: Printer[Rows]): Printer[Columns] = columns =>
    p.print(columns.toRows)

  implicit val row: Printer[Row] = _.cells.mkString(" ")

  implicit def rows(implicit p: Printer[Row]): Printer[Rows] = rows =>
    rows.values.map(p.print).mkString("\n")

  implicit def table(implicit p: Printer[Rows]): Printer[Table] = table =>
    p.print(table.rows)
}