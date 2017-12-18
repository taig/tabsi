package io.taig.tabsi.extension.syntax.operation

import io.taig.tabsi.Printer

final class print[F[_]](val value: F[String]) {
  def print(implicit p: Printer[F]): String = p.print(value)
}
