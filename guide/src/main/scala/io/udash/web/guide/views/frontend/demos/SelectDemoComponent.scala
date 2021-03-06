package io.udash.web.guide.views.frontend.demos

import io.udash._
import io.udash.web.guide.styles.BootstrapStyles
import io.udash.web.guide.styles.partials.GuideStyles
import org.scalajs.dom.Element

import scalatags.JsDom

class SelectDemoComponent extends Component {
  import io.udash.web.guide.Context._
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

  override def getTemplate: Element = div(id := "select-demo", GuideStyles.frame)(
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
    div(BootstrapStyles.inputGroupAddon)(
      Select(
        favoriteFruitString, Seq(Apple, Orange, Banana).map(_.toString),
        BootstrapStyles.formControl
      )
    ),
    div(BootstrapStyles.inputGroupAddon)(span(cls := "select-demo-fruits")(bind(favoriteFruit)))
  )
}
