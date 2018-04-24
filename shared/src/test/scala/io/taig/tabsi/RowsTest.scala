//package io.taig.tabsi
//
//import com.github.dwickern.macros.NameOf._
//import org.scalatest.{Matchers, WordSpec}
//
//class RowsTest extends WordSpec with Matchers {
//  s"${nameOf[Rows.type](_.normalize _)}" should {
//    s"not affect empty ${nameOfType[Rows[_]]}" in {
//      Rows.normalize(Rows.empty[Cell]) shouldBe Rows.empty[Cell]
//    }
//
//    s"not affect normalized ${nameOfType[Rows[_]]}" in {
//      val rows = Rows(
//        Row(Cell("1"), Cell("2"), Cell("3")),
//        Row(Cell("4"), Cell("5"), Cell("6"))
//      )
//      
//      Rows.normalize(rows) shouldBe rows
//    }
//    
//    s"affect demoralized ${nameOfType[Rows[_]]}" in {
//      val demoralized = Rows(
//        Row(Cell("1"), Cell("2")),
//        Row(Cell("4"), Cell("5"), Cell("6"), Cell("7"))
//      )
//
//      val normalized = Rows(
//        Row(Cell("1"), Cell("2"), Cell.Empty, Cell.Empty),
//        Row(Cell("4"), Cell("5"), Cell("6"), Cell("7"))
//      )
//
//      Rows.normalize(demoralized) shouldBe normalized
//    }
//  }
//}