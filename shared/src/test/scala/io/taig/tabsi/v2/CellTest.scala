package io.taig.tabsi.v2

import com.github.dwickern.macros.NameOf._
import io.taig.tabsi.{Cell, Column, Row}
import org.scalatest.{Matchers, WordSpec}

class CellTest extends WordSpec with Matchers {
  s"${nameOf[Cell](_.combine _)} with a ${nameOfType[Cell]}" should {
    s"yield a ${nameOfType[Column]} via ${nameOfType[Direction.Bottom.type]}" in {
      Cell("a").combine(Direction.Bottom)(Cell("b")) shouldBe Some(Column(Cell("a"), Cell("b")))
    }

    s"yield a ${nameOfType[Column]} via ${nameOfType[Direction.Top.type]}" in {
      Cell("a").combine(Direction.Top)(Cell("b")) shouldBe Some(Column(Cell("b"), Cell("a")))
    }

    s"yield a ${nameOfType[Row]} via ${nameOfType[Direction.Right.type]}" in {
      Cell("a").combine(Direction.Right)(Cell("b")) shouldBe Some(Row(Cell("a"), Cell("b")))
    }

    s"yield a ${nameOfType[Row]} via ${nameOfType[Direction.Left.type]}" in {
      Cell("a").combine(Direction.Left)(Cell("b")) shouldBe Some(Row(Cell("b"), Cell("a")))
    }
  }

  s"${nameOf[Cell](_.combine _)} with a ${nameOfType[Column]}" should {
    s"yield a ${nameOfType[Column]} via ${nameOfType[Direction.Bottom.type]}" in {
      Cell("a").combine(Direction.Bottom)(Column(Cell("b"), Cell("c"))) shouldBe Some(Column(Cell("a"), Cell("b"), Cell("c")))
    }

    s"yield a ${nameOfType[Column]} via ${nameOfType[Direction.Top.type]}" in {
      Cell("a").combine(Direction.Top)(Column(Cell("b"), Cell("c"))) shouldBe Some(Column(Cell("b"), Cell("c"), Cell("a")))
    }

    s"yield a ${nameOfType[Row]} via ${nameOfType[Direction.Right.type]}" in {
      Cell("a").combine(Direction.Right)(Column(Cell("b"))) shouldBe Some(Row(Cell("a"), Cell("b")))
    }
    
    s"yield a None via ${nameOfType[Direction.Right.type]}" in {
      Cell("a").combine(Direction.Right)(Column(Cell("b"), Cell("c"))) should not be defined
    }

    s"yield a ${nameOfType[Row]} via ${nameOfType[Direction.Left.type]}" in {
      Cell("a").combine(Direction.Left)(Column(Cell("b"))) shouldBe Some(Row(Cell("b"), Cell("a")))
    }

    s"yield a None via ${nameOfType[Direction.Left.type]}" in {
      Cell("a").combine(Direction.Left)(Column(Cell("b"), Cell("c"))) should not be defined
    }
  }
}