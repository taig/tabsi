package io.taig.tabsi.extension.syntax.operation

import io.taig.tabsi._

final class format[F[_], A](val value: F[A]) extends AnyVal {
  def format(styles: Styles)(implicit f: Formatter[F, A]): F[String] =
    f.format(value, styles)
}
