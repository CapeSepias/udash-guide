package io.udash.web.guide.views.ext

import io.udash._
import io.udash.web.commons.components.CodeBlock
import io.udash.web.guide._
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.views.ext.demo.BootstrapDemos
import io.udash.web.guide.views.{References, Versions}
import org.scalajs.dom

import scalatags.JsDom

case object BootstrapExtViewPresenter extends DefaultViewPresenterFactory[BootstrapExtState.type](() => new BootstrapExtView)


class BootstrapExtView extends View {
  import JsDom.all._

  override def getTemplate: dom.Element = div(
    h1("Udash Bootstrap wrapper"),
    p("..."),
    h2("First steps"),
    p("To start development with the Bootstrap wrapper add the following line in you frontend module dependencies: "),
    CodeBlock(
      s""""io.udash" %%% "udash-bootstrap" % "${Versions.udashVersion}"""".stripMargin
    )(GuideStyles),
    p("The wrapper provides a typed equivalent of the ", a(href := References.bootstrapHomepage)("Twitter Bootstrap"), " API."),
    h2("Statics"),
    p(s"All Bootstrap tags and styles are available as ScalaCSS applicable styles (", i("StyleA"), ")."),
    CodeBlock(
      s"""|div(BootstrapStyles.row)(
          |  div(BootstrapStyles.Grid.colXs9, BootstrapStyles.Well.well)(
          |    ".col-xs-9"
          |  ),
          |  div(BootstrapStyles.Grid.colXs4, BootstrapStyles.Well.well)(
          |    ".col-xs-4",br,
          |    "Since 9 + 4 = 13 > 12, this 4-column-wide div",
          |    "gets wrapped onto a new line as one contiguous unit."
          |  ),
          |  div(BootstrapStyles.Grid.colXs6, BootstrapStyles.Well.well)(
          |    ".col-xs-6",br,
          |    "Subsequent columns continue along the new line."
          |  )
          |)""".stripMargin
    )(GuideStyles),
    BootstrapDemos.styles(),
    h2("Components"),
    p("..."),
    h3("Glyphicons & FontAwesome"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Dropdowns"),
    p("You can create dynamic dropdowns using ", i("SeqProperty"), "-based ", i("UdashDropdown"),
      ". It allows listening on item selection and using custom item renderers."),
    p("Example below shows a simple dropup using default renderer and item styles. A new item is added every 5 seconds ",
      "and item selections are recorded and displayed underneath."),
    CodeBlock(
      s"""|val items = SeqProperty[UdashDropdown.DefaultDropdownItem](Seq(
          |  UdashDropdown.DropdownHeader("Start"),
          |  UdashDropdown.DropdownLink("Intro", url),
          |  UdashDropdown.DropdownDisabled(UdashDropdown.DropdownLink("Test Disabled", url)),
          |  UdashDropdown.DropdownDivider,
          |  UdashDropdown.DropdownHeader("Dynamic")
          |))
          |
          |val clicks = SeqProperty[String](Seq.empty)
          |val listener: UdashDropdown[UdashDropdown.DefaultDropdownItem]#EventHandler = {
          |  case UdashDropdown.SelectionEvent(_, item) =>
          |    clicks.append(item.toString)
          |  case ev: DropdownEvent[_] =>
          |    logger.info(ev.toString)
          |}
          |
          |val dropdown = UdashDropdown.dropup(items)("Dropdown")
          |val dropup = UdashDropdown(items)("Dropup")
          |dropdown.listen(listener)
          |dropup.listen(listener)
          |
          |div(
          |  dropdown.render,
          |  dropup.render,
          |  bind(clicks)
          |)""".stripMargin
    )(GuideStyles),
    BootstrapDemos.dropdown(),
    h3("Button"),
    p("Bootstrap buttons are easy to use as ", i("UdashButton"), "s. They support click listening, ",
      "provide typesafe style & size classes and a ", i("Property"), "-based mechanism for activation and disabling."),
    p("This example shows a variety of available button options. Small button indicators register their clicks and are ",
      "randomly set as active or disabled by the block button action, which also clears the click history."),
    CodeBlock(
      s"""|val buttons = Seq(
          |  UdashButton(size = ButtonSize.Small)("Default"),
          |  UdashButton(ButtonStyle.Primary, ButtonSize.Small)("Primary"),
          |  UdashButton(ButtonStyle.Success, ButtonSize.Small)("Success"),
          |  UdashButton(ButtonStyle.Info, ButtonSize.Small)("Info") ,
          |  UdashButton(ButtonStyle.Warning, ButtonSize.Small)("Warning") ,
          |  UdashButton(ButtonStyle.Danger, ButtonSize.Small)("Danger"),
          |  UdashButton(ButtonStyle.Link, ButtonSize.Small)("Link")
          |)
          |
          |val clicks = SeqProperty[String](Seq.empty)
          |buttons.foreach(_.listen {
          |  case ev => clicks.append(ev.button.render.textContent)
          |})
          |
          |val push = UdashButton(size = ButtonSize.Large, block = true)(
          |  "Push the button!"
          |)
          |push.listen {
          |  case _ =>
          |    clicks.set(Seq.empty)
          |    buttons.foreach(button => {
          |      val random = Random.nextBoolean()
          |      button.disabled.set(random)
          |    })
          |}
          |
          |div(
          |  push.render,
          |  buttons.map(_.render)
          |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.button(),
    p("The below example presents helper method for creating toggle buttons."),
    CodeBlock(
      s"""|val buttons = Seq(
          |  UdashButton.toggle()("Default"),
          |  UdashButton.toggle(ButtonStyle.Primary)("Primary"),
          |  UdashButton.toggle(ButtonStyle.Success)("Success"),
          |  UdashButton.toggle(ButtonStyle.Info)("Info"),
          |  UdashButton.toggle(ButtonStyle.Warning)("Warning") ,
          |  UdashButton.toggle(ButtonStyle.Danger)("Danger"),
          |  UdashButton.toggle(ButtonStyle.Link)("Link")
          |)
          |
          |div(
          |  buttons.map(_.render)
          |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.toggleButton(),
    h3("Button groups"),
    p("There are many ways of creating a button group. The first example presents static API usage:"),
    CodeBlock(
      s"""UdashButtonGroup(vertical = true)(
         |  UdashButton(style = ButtonStyle.Primary)("Button 1").render,
         |  UdashButton()("Button 2").render,
         |  UdashButton()("Button 3").render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.staticButtonsGroup(),
    p("It is also possible to create reactive groups and toolbars:"),
    CodeBlock(
      s"""val groups = SeqProperty[Seq[Int]](Seq[Seq[Int]](1 to 4, 5 to 7, 8 to 8))
         |UdashButtonToolbar.reactive(groups, (p: CastableProperty[Seq[Int]]) => {
         |  val range = p.asSeq[Int]
         |  UdashButtonGroup.reactive(range, size = ButtonSize.Large)(element =>
         |    UdashButton()(element.get).render
         |  ).render
         |}).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.buttonToolbar(),
    p("Use ", i("checkboxes"), " method in order to create a group of buttons behaving as checkboxes:"),
    CodeBlock(
      s"""import UdashButtonGroup._
         |val options = SeqProperty[CheckboxModel](Seq(
         |  DefaultCheckboxModel("Checkbox 1 (pre-checked)", true),
         |  DefaultCheckboxModel("Checkbox 2", false),
         |  DefaultCheckboxModel("Checkbox 3", false)
         |))
         |div(
         |  UdashButtonGroup.checkboxes(options)(defaultCheckboxFactory).render,
         |  h4("Is active: "),
         |  div(BootstrapStyles.Well.well)(
         |    repeat(options)(option => {
         |      val model = option.asModel
         |      val name = model.subProp(_.text)
         |      val checked = model.subProp(_.checked)
         |      div(bind(name), ": ", bind(checked)).render
         |    })
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.checkboxButtons(),
    p("The following example presents a group of buttons behaving as radio buttons:"),
    CodeBlock(
      s"""import UdashButtonGroup._
         |val options = SeqProperty[CheckboxModel](Seq(
         |  DefaultCheckboxModel("Radio 1 (preselected)", true),
         |  DefaultCheckboxModel("Radio 2", false),
         |  DefaultCheckboxModel("Radio 3", false)
         |))
         |div(
         |  UdashButtonGroup.radio(options, justified = true).render,
         |  h4("Is active: "),
         |  div(BootstrapStyles.Well.well)(
         |    repeat(options)(option => {
         |      val model = option.asModel
         |      val name = model.subProp(_.text)
         |      val checked = model.subProp(_.checked)
         |      div(bind(name), ": ", bind(checked)).render
         |    })
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.radioButtons(),
    h3("Button dropdowns"),
    p("The ", i("UdashDropdown"), " component can be used as part of a button group."),
    CodeBlock(
      s"""val items = SeqProperty[DefaultDropdownItem](Seq(
         |  UdashDropdown.DropdownHeader("Start"),
         |  UdashDropdown.DropdownLink("Intro", Url("#")),
         |  UdashDropdown.DropdownDisabled(UdashDropdown.DropdownLink("Test Disabled", Url("#"))),
         |))
         |UdashButtonToolbar(
         |  UdashButtonGroup()(
         |    UdashButton()("Button").render,
         |    UdashDropdown(items)().render,
         |    UdashDropdown.dropup(items)().render
         |  ).render,
         |  UdashDropdown(items)("Dropdown ").render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.buttonDropdown(),
    h3("Input groups"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Navs"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Navbar"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Breadcrumbs"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Pagination"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Labels"),
    CodeBlock(
      s"""UdashLabel("Default").render,
         |UdashLabel.primary("Primary").render,
         |UdashLabel.success("Success").render,
         |UdashLabel.info("Info").render,
         |UdashLabel.warning("Warning").render,
         |UdashLabel.danger("Danger").render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.labels(),
    h3("Badges"),
    CodeBlock(
      s"""val counter = Property(0)
         |window.setInterval(() => counter.set(counter.get + 1), 3000)
         |UdashButton(style = ButtonStyle.Primary, size = ButtonSize.Large)
         |           ("Button", UdashBadge(counter).render).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.badges(),
    h3("Jumbotron"),
    p("..."),
    CodeBlock(
      s"""UdashJumbotron(h1("Header), "Content...").render""".stripMargin
    )(GuideStyles),
    h3("Page header"),
    CodeBlock(
      s"""UdashPageHeader(h1("Header ", small("Subtext"))).render""".stripMargin
    )(GuideStyles),
    h3("Thumbnails"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Alerts"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Progress bars"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Media object"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("List group"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Panels"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Responsive embed"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Wells"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Modals"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("ScrollSpy"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Tabs"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Tooltips"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Popovers"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Collapse"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Carousel"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h2("What's next?"),
    p("...")
  ).render

  override def renderChild(view: View): Unit = {}
}