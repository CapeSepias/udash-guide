package io.udash.web.guide.views.ext.demo

import io.udash._
import io.udash.bootstrap.alert.{AlertStyle, UdashAlert}
import io.udash.bootstrap.{BootstrapStyles, UdashBootstrap}
import io.udash.bootstrap.button._
import io.udash.bootstrap.dropdown.UdashDropdown
import io.udash.bootstrap.dropdown.UdashDropdown.{DefaultDropdownItem, DropdownEvent}
import io.udash.bootstrap.pagination.Pagination
import io.udash.bootstrap.utils.{Icons, UdashBadge, UdashLabel, UdashPageHeader}
import io.udash.properties.SeqProperty
import io.udash.web.commons.styles.GlobalStyles
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.styles.utils.StyleUtils
import io.udash.web.guide.{BootstrapExtState, Context, IntroState}
import org.scalajs.dom

import scala.collection.mutable
import scala.util.Random
import scalatags.JsDom

object BootstrapDemos extends StrictLogging {

  import io.udash.web.guide.Context._
  import org.scalajs.dom._

  import JsDom.all._
  import scalacss.ScalatagsCss._

  def forceBootstrapStyle(): dom.Element =
    UdashBootstrap.loadBootstrapStyles()

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

  def icons(): dom.Element =
    div(GuideStyles.frame)(
      UdashBootstrap.loadFontAwesome(),
      UdashButtonToolbar(
        UdashButtonGroup()(
          UdashButton()(Icons.Glyphicon.alignLeft).render,
          UdashButton()(Icons.Glyphicon.alignCenter).render,
          UdashButton()(Icons.Glyphicon.alignRight).render,
          UdashButton()(Icons.Glyphicon.alignJustify).render
        ).render,
        UdashButtonGroup()(
          UdashButton()(Icons.FontAwesome.bitcoin).render,
          UdashButton()(Icons.FontAwesome.euro).render,
          UdashButton()(Icons.FontAwesome.dollar).render
        ).render
      ).render
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

    val dropdown = UdashDropdown(items)("Dropdown ", BootstrapStyles.Button.btnPrimary)
    val dropup = UdashDropdown.dropup(items)("Dropup ")
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
      div(GlobalStyles.centerBlock)(
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
      div(GlobalStyles.centerBlock)(
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

  def staticButtonsGroup(): dom.Element = {
    div(StyleUtils.center, GuideStyles.frame)(
      UdashButtonGroup(vertical = true)(
        UdashButton(style = ButtonStyle.Primary)("Button 1").render,
        UdashButton()("Button 2").render,
        UdashButton()("Button 3").render
      ).render
    ).render
  }

  def buttonToolbar(): dom.Element = {
    val groups = SeqProperty[Seq[Int]](Seq[Seq[Int]](1 to 4, 5 to 7, 8 to 8))
    div(StyleUtils.center, GuideStyles.frame)(
      UdashButtonToolbar.reactive(groups, (p: CastableProperty[Seq[Int]]) => {
        val range = p.asSeq[Int]
        UdashButtonGroup.reactive(range, size = ButtonSize.Large)(element =>
          UdashButton()(element.get).render
        ).render
      }
      ).render
    ).render
  }

  def checkboxButtons(): dom.Element = {
    import UdashButtonGroup._
    val options = SeqProperty[CheckboxModel](Seq(
      DefaultCheckboxModel("Checkbox 1 (pre-checked)", true),
      DefaultCheckboxModel("Checkbox 2", false),
      DefaultCheckboxModel("Checkbox 3", false)
    ))
    div(StyleUtils.center, GuideStyles.frame)(
      UdashButtonGroup.checkboxes(options)(defaultCheckboxFactory).render,
      h4("Is active: "),
      div(BootstrapStyles.Well.well)(
        repeat(options)(option => {
          val model = option.asModel
          val name = model.subProp(_.text)
          val checked = model.subProp(_.checked)
          div(bind(name), ": ", bind(checked)).render
        })
      )
    ).render
  }

  def radioButtons(): dom.Element = {
    import UdashButtonGroup._
    val options = SeqProperty[CheckboxModel](Seq(
      DefaultCheckboxModel("Radio 1 (preselected)", true),
      DefaultCheckboxModel("Radio 2", false),
      DefaultCheckboxModel("Radio 3", false)
    ))
    div(StyleUtils.center, GuideStyles.frame)(
      UdashButtonGroup.radio(options, justified = true).render,
      h4("Is active: "),
      div(BootstrapStyles.Well.well)(
        repeat(options)(option => {
          val model = option.asModel
          val name = model.subProp(_.text)
          val checked = model.subProp(_.checked)
          div(bind(name), ": ", bind(checked)).render
        })
      )
    ).render
  }

  def buttonDropdown(): dom.Element = {
    val items = SeqProperty[DefaultDropdownItem](Seq(
      UdashDropdown.DropdownHeader("Start"),
      UdashDropdown.DropdownLink("Intro", Url("#")),
      UdashDropdown.DropdownDisabled(UdashDropdown.DropdownLink("Test Disabled", Url("#"))),
      UdashDropdown.DropdownDivider,
      UdashDropdown.DropdownHeader("End"),
      UdashDropdown.DropdownLink("Intro", Url("#"))
    ))
    div(StyleUtils.center, GuideStyles.frame)(
      UdashButtonToolbar(
        UdashButtonGroup()(
          UdashButton()("Button").render,
          UdashDropdown(items)().render,
          UdashDropdown.dropup(items)().render
        ).render,
        UdashDropdown(items)("Dropdown ").render
      ).render
    ).render
  }

  def pagination(): dom.Element = {
    import Pagination._
    import Context._

    val showArrows = Property(true)
    val highlightActive = Property(true)
    val toggleArrows = UdashButton.toggle(active = showArrows)("Toggle arrows")
    val toggleHighlight = UdashButton.toggle(active = highlightActive)("Toggle highlight")

    val pages = SeqProperty(Seq.tabulate[Page](7)(idx =>
      DefaultPage((idx+1).toString, Url(applicationInstance.currentState.url))
    ))
    val selected = Property(0)
    val pagination = Pagination(
      showArrows = showArrows, highlightActive = highlightActive
    )(pages, selected)(Pagination.defaultPageFactory)
    val pager = Pagination.pager()(pages, selected)(Pagination.defaultPageFactory)
    div(StyleUtils.center, GuideStyles.frame)(
      div("Selected page index: ", bind(selected)),
      div(
        UdashButtonGroup()(
          toggleArrows.render,
          toggleHighlight.render
        ).render
      ),
      div(GlobalStyles.centerBlock)(pagination.render),
      pager.render
    ).render
  }

  def labels(): dom.Element = {
    div(StyleUtils.center, GuideStyles.frame)(
      forceBootstrapStyle(),
      UdashLabel("Default", GlobalStyles.smallMargin).render,
      UdashLabel.primary("Primary", GlobalStyles.smallMargin).render,
      UdashLabel.success("Success", GlobalStyles.smallMargin).render,
      UdashLabel.info("Info", GlobalStyles.smallMargin).render,
      UdashLabel.warning("Warning", GlobalStyles.smallMargin).render,
      UdashLabel.danger("Danger", GlobalStyles.smallMargin).render
    ).render
  }

  def badges(): dom.Element = {
    val counter = Property(0)
    window.setInterval(() => counter.set(counter.get + 1), 3000)
    div(StyleUtils.center, GuideStyles.frame)(
      forceBootstrapStyle(),
      UdashButton(style = ButtonStyle.Primary, size = ButtonSize.Large)("Button ", UdashBadge(counter).render).render
    ).render
  }

  def alerts(): dom.Element = {
    val styles = Seq(AlertStyle.Info, AlertStyle.Danger,
      AlertStyle.Success, AlertStyle.Warning)
    val dismissed = SeqProperty[String](Seq.empty)
    def randomDismissible(): dom.Element = {
      val title = randomString()
      val alert = UdashAlert.dismissible(
        styles(Random.nextInt(styles.size))
      )(title)
      alert.dismissed.listen(_ => dismissed.append(title))
      alert.render
    }
    val alerts = div(BootstrapStyles.Well.well, GlobalStyles.centerBlock)(
      UdashAlert.info("info").render,
      UdashAlert.success("success").render,
      UdashAlert.warning("warning").render,
      UdashAlert.danger("danger").render
    ).render
    val create = UdashButton(
      size = ButtonSize.Large
    )("Create dismissible alert")
    create.listen { case _ => alerts.appendChild(randomDismissible()) }
    div(StyleUtils.center, GuideStyles.frame)(
      create.render,
      alerts,
      h4("Dismissed: "),
      produce(dismissed)(seq =>
        ul(BootstrapStyles.Well.well)(seq.map(click =>
          li(click)
        ): _*).render
      )
    ).render
  }

  private def randomString(): String = BigInt.probablePrime(100, Random).toString(36)
}
