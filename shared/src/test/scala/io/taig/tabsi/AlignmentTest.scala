package io.taig.tabsi

import org.scalatest.{Matchers, WordSpec}
import com.github.dwickern.macros.NameOf._

class AlignmentTest extends WordSpec with Matchers {
  s"${nameOf[Alignment](_.align _)}" should {
    s"add a right padding for ${nameOfType[Alignment.Left.type]}" in {
      Alignment.Left.align("foo", 2) shouldBe "foo  "
    }

    s"add a left padding for ${nameOfType[Alignment.Right.type]}" in {
      Alignment.Right.align("foo", 2) shouldBe "  foo"
    }

    s"add a left and right padding for ${nameOfType[Alignment.Center.type]}" in {
      Alignment.Center.align("foo", 2) shouldBe " foo "
    }

    s"offset to the left when aligning ${nameOfType[Alignment.Right.type]} with uneven padding" in {
      Alignment.Center.align("foo", 3) shouldBe " foo  "
    }
  }
}
