package io.taig.tabsi.extension.syntax

import io.taig.tabsi.Table

trait table {
  implicit def tabsiTableFormatSyntax[A](table: Table[A]): operation.format[Table, A] =
    new operation.format[Table, A](table)

  implicit def tabsiTablePrintSyntax(table: Table[String]): operation.print[Table] =
    new operation.print[Table](table)

  implicit def tabsiTablePrintfSyntax[A](table: Table[A]): operation.printf[Table, A] =
    new operation.printf[Table, A](table)
}

object table extends table