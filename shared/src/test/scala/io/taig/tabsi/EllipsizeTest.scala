//package io.taig.tabsi
//
//import com.github.dwickern.macros.NameOf._
//import org.scalatest.{Matchers, WordSpec}
//
//class EllipsizeTest extends WordSpec with Matchers {
//  s"${nameOf[Ellipsize](_.truncate _)}" should {
//    s"append an ellipsis for ${nameOfType[Ellipsize.End.type]}" in {
//      Ellipsize.End.truncate("foobar", 3) shouldBe s"foo${Ellipsize.Character}"
//    }
//
//    s"prepend an ellipsis for ${nameOfType[Ellipsize.Start.type]}" in {
//      Ellipsize.Start
//        .truncate("foobar", 3) shouldBe s"${Ellipsize.Character}bar"
//    }
//
//    s"inject an ellipsis for ${nameOfType[Ellipsize.Middle.type]}" in {
//      Ellipsize.Middle
//        .truncate("foobar", 2) shouldBe s"fo${Ellipsize.Character}ar"
//    }
//
//    s"offset to the left when aligning ${nameOfType[Ellipsize.Middle.type]} with uneven padding" in {
//      Ellipsize.Middle
//        .truncate("foobar", 3) shouldBe s"f${Ellipsize.Character}ar"
//    }
//  }
//}
