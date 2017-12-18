package io.taig.tabsi

import com.github.dwickern.macros.NameOf._
import org.scalatest.{Matchers, WordSpec}

class CellTest extends WordSpec with Matchers {
  s"${nameOf[Cell.Value](_.format _)}" should {
    s"return the ${nameOfType[Cell]}.${nameOfType[Cell.Value]} value when the width equals the value length" in {
      Cell.Value("foo").format(Alignment.Center, 3, None) shouldBe "foo"
      Cell.Value("foo").format(Alignment.Left, 3, None) shouldBe "foo"
      Cell.Value("foo").format(Alignment.Right, 3, None) shouldBe "foo"
    }

    s"add padding when the width is bigger than the value length" in {
      Cell.Value("foo").format(Alignment.Center, 5, None) shouldBe " foo "
      Cell.Value("foo").format(Alignment.Left, 5, None) shouldBe "foo  "
      Cell.Value("foo").format(Alignment.Right, 5, None) shouldBe "  foo"
    }

    s"cut off the value when the width is lower than the value length and no ${nameOfType[Ellipsize]} is given" in {
      Cell.Value("foobar").format(Alignment.Center, 3, None) shouldBe "foo"
      Cell.Value("foobar").format(Alignment.Left, 3, None) shouldBe "foo"
      Cell.Value("foobar").format(Alignment.Right, 3, None) shouldBe "foo"
    }

    s"ellipsize the value the width is lower than the value length and an ${nameOfType[Ellipsize]} is given" in {
      Cell.Value("foobar").format(Alignment.Center, 3, Some(Ellipsize.End)) should contain(
        Ellipsize.Character)
      Cell.Value("foobar").format(Alignment.Center, 3, Some(Ellipsize.Middle)) should contain(
        Ellipsize.Character)
      Cell.Value("foobar").format(Alignment.Center, 3, Some(Ellipsize.Start)) should contain(
        Ellipsize.Character)
    }

    s"not exceed the width when the ${nameOfType[Ellipsize]} character is added" in {
      Cell.Value("foobar").format(Alignment.Left, 3, Some(Ellipsize.End)) shouldBe s"fo${Ellipsize.Character}"
    }

    s"not ellipsize when the cell width equals the fixed width" in {
      Cell.Value("foo").format( Alignment.Left, 3, Some(Ellipsize.End)) shouldBe s"foo"
    }

    s"return a whitespace String for ${nameOfType[Cell]}.${nameOfType[Cell.Empty.type]}" in {
      Cell.Empty.format(Alignment.Left, 0, None) shouldBe ""
      Cell.Empty.format(Alignment.Left, 3, None) shouldBe "   "
    }
  }
}
