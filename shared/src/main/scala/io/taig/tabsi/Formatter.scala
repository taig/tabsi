package io.taig.tabsi

abstract class Formatter[F[_]] {
  def format(value: F[Cell], styles: Styles): F[String]
}

object Formatter {
  def apply[F[_]](implicit f: Formatter[F]): Formatter[F] = f

  private val EllipsisCharacter: Char = '…'

  private def cellStyles(limit: Int, width: Width): (Int, Option[Ellipsis]) =
    width match {
      case Width.Auto => (limit, None)
      case Width.Fixed(value, ellipsize) => (value, ellipsize)
      case Width.Limit(value, ellipsize) => (math.min(limit, value), ellipsize)
    }

  private def align(alignment: Alignment, value: String, padding: Int): String =
    alignment match {
      case Alignment.Center =>
        val left = math.floor(padding / 2d).toInt
        val right = math.ceil(padding / 2d).toInt
        " " * left + value + " " * right
      case Alignment.Left => value + " " * padding
      case Alignment.Right => " " * padding + value
    }

  private def truncate(ellipsis: Ellipsis, value: String, padding: Int): String =
    ellipsis match {
      case Ellipsis.End =>
        value.substring(0, value.length - padding) + EllipsisCharacter
      case Ellipsis.Middle =>
        val center = value.length / 2f
        val offset = padding / 2f
        val left = (center - offset).toInt
        val right = (center + offset).toInt
        value.substring(0, left) + EllipsisCharacter + value.substring(right)
      case Ellipsis.Start =>
        EllipsisCharacter + value.substring(padding)
    }

  private def formatCell(cell: Cell, alignment: Alignment, width: Int, ellipsize: Option[Ellipsis]): String =
    cell match {
      case Cell.Empty => " " * width
      case Cell.Value(value) =>
        if(width == value.length) value
        else if(width > value.length) {
          val padding = width - value.length
          align(alignment, value, padding)
        }
        else {
          val padding = value.length - width + 1
          ellipsize.fold(value.substring(0, width))(truncate(_, value, padding))
        }
    }

  implicit val column: Formatter[Column] = { (column, styles) =>
    val style = styles.values.head
    val limit = column.values.map(_.size).toList.max
    val (width, ellipsize) = cellStyles(limit, style.width)
    Column(column.values.map(formatCell(_, style.alignment, width, ellipsize)))
  }
}