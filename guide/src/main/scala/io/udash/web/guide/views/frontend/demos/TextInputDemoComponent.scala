package io.udash.web.guide.views.frontend.demos

import io.udash._
import io.udash.web.guide.styles.BootstrapStyles
import io.udash.web.guide.styles.partials.GuideStyles
import org.scalajs.dom.Element

import scalatags.JsDom

class TextInputDemoComponent extends Component {
  import io.udash.web.guide.Context._
  import JsDom.all._
  import scalacss.ScalatagsCss._

  val name: Property[String] = Property("")
  val password: Property[String] = Property("")
  val age: Property[Int] = Property(1)

  override def getTemplate: Element = div(id := "inputs-demo", GuideStyles.frame)(
    form(BootstrapStyles.containerFluid)(
      inputs, br, inputs
    )
  ).render

  private val inputs = div(BootstrapStyles.row)(
    div(BootstrapStyles.colMd4)(
      div(BootstrapStyles.inputGroup)(
        div(BootstrapStyles.inputGroupAddon)("Name:"),
        TextInput(name)(BootstrapStyles.formControl, placeholder := "Input your name...", maxlength := "6"),
        div(BootstrapStyles.inputGroupAddon)(bind(name))
      )
    ),
    div(BootstrapStyles.colMd4)(
      div(BootstrapStyles.inputGroup)(
        div(BootstrapStyles.inputGroupAddon)("Password:"),
        PasswordInput(password)(BootstrapStyles.formControl, placeholder := "Input your password...", maxlength := "6"),
        div(BootstrapStyles.inputGroupAddon)(bind(password))
      )
    ),
    div(BootstrapStyles.colMd4)(
      div(BootstrapStyles.inputGroup)(
        div(BootstrapStyles.inputGroupAddon)("Age:"),
        NumberInput(age.transform(_.toString, Integer.parseInt), maxlength := "6")(BootstrapStyles.formControl),
        div(BootstrapStyles.inputGroupAddon)(bind(age))
      )
    )
  )
}
