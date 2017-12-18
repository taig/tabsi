package io.taig.tabsi.extension.syntax.operation

import io.taig.tabsi.{Formatter, Printer, Styles}

final class printf[F[_], A](val value: F[A]) {
  def printf(styles: Styles)(implicit f: Formatter[F, A], p: Printer[F]): String =
    p.print(f.format(value, styles))
}
