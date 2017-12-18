package io.taig.tabsi

trait Formatter[F[_], A] {
  def format(value: F[A], styles: Styles): F[String]
}

object Formatter {
  def apply[F[_], A](implicit f: Formatter[F, A]): Formatter[F, A] = f

  private def cellStyles(limit: Int,
                         width: Width): (Int, Option[Ellipsize]) = width match {
    case Width.Auto                    => (limit, None)
    case Width.Fixed(value, ellipsize) => (value, ellipsize)
    case Width.Limit(value, ellipsize) => (math.min(limit, value), ellipsize)
  }

  implicit val column: Formatter[Column, Cell] = { (column, styles) =>
    val style = styles.values.head
    val limit = column.cells.map(_.size).max
    val (width, ellipsize) = cellStyles(limit, style.width)
    column.map(_.format(style.alignment, width, ellipsize))
  }

  implicit val columns: Formatter[Columns, Cell] = { (columns, styles) =>
    val formattedColumns = (columns.values zip styles.values).map {
      case (column, style) =>
        Formatter[Column, Cell].format(column, Styles(style))
    }

    Columns(formattedColumns)
  }

  implicit val row: Formatter[Row, Cell] = { (row, styles) =>
    val cells = (row.cells zip styles.values).map {
      case (cell, style) =>
        val (limit, ellipsize) = cellStyles(cell.size, style.width)
        cell.format(style.alignment, limit, ellipsize)
    }

    Row(cells)
  }

  implicit val rows: Formatter[Rows, Cell] = { (rows, styles) =>
    Formatter[Columns, Cell].format(rows.toColumns, styles).toRows
  }

  implicit val table: Formatter[Table, Cell] = { (table, styles) =>
    Table(Formatter[Columns, Cell].format(table.columns, styles))
  }
}