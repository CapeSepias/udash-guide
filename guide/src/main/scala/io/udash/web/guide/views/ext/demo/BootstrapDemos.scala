package io.udash.web.guide.views.ext.demo

import io.udash._
import io.udash.bootstrap.BootstrapStyles
import io.udash.bootstrap.button.{ButtonSize, ButtonStyle, UdashButton}
import io.udash.bootstrap.dropdown.UdashDropdown
import io.udash.bootstrap.dropdown.UdashDropdown.{DefaultDropdownItem, DropdownEvent}
import io.udash.web.commons.styles.GlobalStyles
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.styles.utils.StyleUtils
import io.udash.web.guide.{BootstrapExtState, IntroState}
import org.scalajs.dom

import scala.collection.mutable
import scala.util.Random
import scalatags.JsDom

object BootstrapDemos extends StrictLogging {

  import io.udash.web.guide.Context._
  import org.scalajs.dom._

  import JsDom.all._
  import scalacss.ScalatagsCss._

  def styles(): dom.Element =
    div(BootstrapStyles.row, GuideStyles.frame)(
      div(BootstrapStyles.Grid.colXs9, BootstrapStyles.Well.well)(
        ".col-xs-9"
      ),
      div(BootstrapStyles.Grid.colXs4, BootstrapStyles.Well.well)(
        ".col-xs-4", br,
        "Since 9 + 4 = 13 > 12, this 4-column-wide div",
        "gets wrapped onto a new line as one contiguous unit."
      ),
      div(BootstrapStyles.Grid.colXs6, BootstrapStyles.Well.well)(
        ".col-xs-6", br,
        "Subsequent columns continue along the new line."
      )
    ).render

  def dropdown(): dom.Element = {
    val url = Url(BootstrapExtState.url)
    val items = SeqProperty[UdashDropdown.DefaultDropdownItem](Seq(
      UdashDropdown.DropdownHeader("Start"),
      UdashDropdown.DropdownLink("Intro", Url(IntroState.url)),
      UdashDropdown.DropdownDisabled(UdashDropdown.DropdownLink("Test Disabled", url)),
      UdashDropdown.DropdownDivider,
      UdashDropdown.DropdownHeader("Dynamic")
    ))

    val clicks = SeqProperty[String](Seq.empty)
    val listener: UdashDropdown[UdashDropdown.DefaultDropdownItem]#EventHandler = {
      case UdashDropdown.SelectionEvent(_, item) => clicks.append(item.toString)
      case ev: DropdownEvent[_] => logger.info(ev.toString)
    }

    var i = 1
    val appendHandler = window.setInterval(() => {
      items.append(UdashDropdown.DropdownLink(s"Test $i", url))
      i += 1
    }, 5000)
    window.setTimeout(() => window.clearInterval(appendHandler), 60000)

    val dropdown = UdashDropdown(items)("Dropdown", BootstrapStyles.Button.btnPrimary)
    val dropup = UdashDropdown.dropup(items)("Dropup")
    dropdown.listen(listener)
    dropup.listen(listener)

    div(GuideStyles.frame)(
      div(BootstrapStyles.row)(
        div(BootstrapStyles.Grid.colXs6)(dropdown.render),
        div(BootstrapStyles.Grid.colXs6)(dropup.render)
      ),
      h4("Clicks: "),
      produce(clicks)(seq =>
        ul(BootstrapStyles.Well.well)(seq.map(click =>
          li(click)
        ): _*).render
      )
    ).render
  }

  def button(): dom.Element = {
    val buttons = Seq(
      UdashButton(size = ButtonSize.Small)("Default", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Primary, ButtonSize.Small)("Primary", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Success, ButtonSize.Small)("Success", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Info, ButtonSize.Small)("Info", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Warning, ButtonSize.Small)("Warning", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Danger, ButtonSize.Small)("Danger", GlobalStyles.smallMargin),
      UdashButton(ButtonStyle.Link, ButtonSize.Small)("Link", GlobalStyles.smallMargin)
    )

    val clicks = SeqProperty[String](Seq.empty)
    buttons.foreach(_.listen {
      case ev => clicks.append(ev.button.render.textContent)
    })

    val push = UdashButton(size = ButtonSize.Large, block = true)("Push the button!")
    push.listen {
      case _ =>
        clicks.set(Seq.empty)
        buttons.foreach(button => {
          val random = Random.nextBoolean()
          button.disabled.set(random)
        })
    }

    div(StyleUtils.center, GuideStyles.frame)(
      push.render,
      div(BootstrapStyles.Well.well, GlobalStyles.centerBlock)(
        buttons.map(b => b.render)
      ),
      h4("Clicks: "),
      produce(clicks)(seq =>
        ul(BootstrapStyles.Well.well)(seq.map(click =>
          li(click)
        ): _*).render
      )
    ).render
  }

  def toggleButton(): dom.Element = {
    val buttons = mutable.LinkedHashMap[String, UdashButton](
      "Default" -> UdashButton.toggle()("Default", GlobalStyles.smallMargin),
      "Primary" -> UdashButton.toggle(ButtonStyle.Primary)("Primary", GlobalStyles.smallMargin),
      "Success" -> UdashButton.toggle(ButtonStyle.Success)("Success", GlobalStyles.smallMargin),
      "Info" -> UdashButton.toggle(ButtonStyle.Info)("Info", GlobalStyles.smallMargin),
      "Warning" -> UdashButton.toggle(ButtonStyle.Warning)("Warning", GlobalStyles.smallMargin),
      "Danger" -> UdashButton.toggle(ButtonStyle.Danger)("Danger", GlobalStyles.smallMargin),
      "Link" -> UdashButton.toggle(ButtonStyle.Link)("Link", GlobalStyles.smallMargin)
    )

    div(StyleUtils.center, GuideStyles.frame)(
      div(BootstrapStyles.Well.well, GlobalStyles.centerBlock)(
        buttons.values.map(b => b.render).toSeq
      ),
      h4("Is active: "),
      div(BootstrapStyles.Well.well)(
        buttons.map({case (name, button) =>
          span(s"$name: ", bind(button.active), br)
        }).toSeq
      )
    ).render
  }
}
