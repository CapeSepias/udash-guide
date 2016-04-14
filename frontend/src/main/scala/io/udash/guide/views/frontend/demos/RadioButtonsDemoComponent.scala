package io.udash.guide.views.frontend.demos

import io.udash._
import io.udash.guide.styles.BootstrapStyles
import io.udash.guide.styles.partials.GuideStyles
import org.scalajs.dom.Element
import org.scalajs.dom.html.Input

import scalatags.JsDom

class RadioButtonsDemoComponent extends Component {
  import io.udash.guide.Context._
  import JsDom.all._
  import scalacss.ScalatagsCss._

  sealed trait Fruit
  case object Apple extends Fruit
  case object Orange extends Fruit
  case object Banana extends Fruit

  val favoriteFruit: Property[Fruit] = Property[Fruit](Apple)
  val favoriteFruitString = favoriteFruit.transform(
    (f: Fruit) => f.toString,
    (s: String) => s match {
      case "Apple" => Apple
      case "Orange" => Orange
      case "Banana" => Banana
    }
  )

  override def getTemplate: Element = div(id := "radio-buttons-demo", GuideStyles.frame)(
    form(BootstrapStyles.containerFluid)(
      div(BootstrapStyles.row)(
        div(
          checkboxes()
        ),
        br(),
        div(
          checkboxes()
        )
      )
    )
  ).render

  def checkboxes() = div(BootstrapStyles.inputGroup, GuideStyles.blockOnMobile)(
    div(BootstrapStyles.inputGroupAddon, GuideStyles.blockOnMobile)("Fruits:"),
    div(BootstrapStyles.inputGroupAddon, GuideStyles.blockOnMobile)(
      RadioButtons(
        favoriteFruitString, Seq(Apple, Orange, Banana).map(_.toString),
        (els: Seq[(Input, String)]) => span(els.map { case (i: Input, l: String) => label(BootstrapStyles.radioInline, "data-label".attr := l)(i, l) })
      )
    ),
    div(BootstrapStyles.inputGroupAddon, GuideStyles.blockOnMobile)(span(cls := "radio-buttons-demo-fruits")(bind(favoriteFruit)))
  )
}