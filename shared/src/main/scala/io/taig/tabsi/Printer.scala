package io.taig.tabsi

import cats.data.NonEmptyList

abstract class Printer[F[_]] {
  def print(value: F[String]): String
}

object Printer {
  def apply[F[_]](implicit p: Printer[F]): Printer[F] = p

  implicit def column(implicit p: Printer[Rows]): Printer[Column] = column =>
    columns(p).print(Columns(NonEmptyList.one(column)))

  implicit def columns(implicit p: Printer[Rows]): Printer[Columns] = columns =>
    p.print(columns.toRows)

  implicit val row: Printer[Row] = _.values.toList.mkString(" ")

  implicit def rows(implicit p: Printer[Row]): Printer[Rows] = rows =>
    rows.values.map(p.print).toList.mkString("\n")
}