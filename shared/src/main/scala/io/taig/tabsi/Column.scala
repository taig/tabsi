package io.taig.tabsi

case class Column[A](cells: Seq[A]) extends AnyVal {
  def height: Int = cells.length

  def append(column: Column[A]): Column[A] = Column(cells ++ column.cells)

  def prepend(column: Column[A]): Column[A] = Column(column.cells ++ cells)

  def map[B](f: A => B): Column[B] = Column(cells.map(f))

  /** Convert this [[Column]] to a [[Rows]] representation, every [[Row]]
    * containing only one `Column`
    */
  def toRows: Rows[A] = Rows( cells.map( Row( _ ) ) )

  override def toString: String = s"Column(${cells.mkString(", ")})"
}

object Column {
  def empty[A]: Column[A] = Column(Seq.empty)

  def apply[A](cell: A, cells: A*): Column[A] = Column(cell +: cells)
}