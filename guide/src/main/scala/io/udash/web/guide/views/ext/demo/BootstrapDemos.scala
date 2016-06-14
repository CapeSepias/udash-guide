package io.udash.web.guide.views.ext.demo

import io.udash._
import io.udash.bootstrap.BootstrapStyles
import io.udash.bootstrap.dropdown.UdashDropdown
import io.udash.web.guide.{BootstrapExtState, IntroState}
import org.scalajs.dom

import scalatags.JsDom

object BootstrapDemos {

  import io.udash.web.guide.Context._
  import org.scalajs.dom._

  import JsDom.all._
  import scalacss.ScalatagsCss._

  def styles(): dom.Element =
    div(BootstrapStyles.row)(
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
    var i = 1
    window.setInterval(() => {
      items.append(UdashDropdown.DropdownLink(s"Test $i", url))
      i += 1
    }, 5000)
    val dropup = UdashDropdown.dropup(items)("Dropup")
    dropup.listen(event => clicks.append(event.item.toString))

    div(
      dropup.render,
      produce(clicks)(seq =>
        ul(BootstrapStyles.Well.well)(seq.map(click =>
          li(BootstrapStyles.row)(click)
        ): _*).render
      )
    ).render
  }
}
