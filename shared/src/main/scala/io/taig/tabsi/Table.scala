package io.taig.tabsi

case class Table[A] private (columns: Columns[A]) {
  def width: Int = columns.width
  
  def height: Int = columns.height

  def rows: Rows[A] = columns.toRows

  def appendBottom(table: Table[A]): Table[A] =
    Table.rows(rows appendBottom table.rows)

  def appendRight(table: Table[A]): Table[A] =
    Table.columns(columns appendRight table.columns)

  def prependLeft(table: Table[A]): Table[A] =
    Table.columns(columns prependLeft table.columns)

  def prependTop(table: Table[A]): Table[A] =
    Table.rows(rows prependTop table.rows)

  override def toString: String = s"Table($columns)"
}

object Table {
  def empty[A]: Table[A] = Table(Columns.empty[A])

  def columns[A](columns: Columns[A]): Table[A] = Table(columns)

  def columns[A](column: Column[A], columns: Column[A]*): Table[A] =
    Table(Columns(column +: columns))

  def rows[A](rows: Rows[A]): Table[A]= Table(rows.toColumns)

  def rows[A](row: Row[A], rows: Row[A]*): Table[A] =
    Table.rows(Rows(row +: rows))
}
