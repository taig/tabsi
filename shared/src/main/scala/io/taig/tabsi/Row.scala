package io.taig.tabsi

case class Row[A](cells: Seq[A]) extends AnyVal {
  def width: Int = cells.length

  def append(row: Row[A]): Row[A] = Row(cells ++ row.cells)

  def prepend(row: Row[A]): Row[A] = Row(row.cells ++ cells)

  def map[B](f: A => B): Row[B] = Row(cells.map(f))

  /** Convert this [[Row]] to a [[Column]] representation, every [[Column]]
    * containing only one `Row`
    */
  def toColumns: Columns[A] = Columns( cells.map( Column( _ ) ) )
}

object Row {
  def empty[A]: Row[A] = Row(Seq.empty)

  def apply[A](cell: A, cells: A*): Row[A] = Row(cell +: cells)
}