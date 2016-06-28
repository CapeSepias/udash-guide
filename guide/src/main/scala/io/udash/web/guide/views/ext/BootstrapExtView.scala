package io.udash.web.guide.views.ext

import io.udash._
import io.udash.web.commons.components.CodeBlock
import io.udash.web.guide._
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.views.ext.demo.BootstrapDemos
import io.udash.web.guide.views.ext.demo.BootstrapDemos.ResetGuideStyles
import io.udash.web.guide.views.{References, Versions}
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLStyleElement

import scalatags.JsDom
import scalatags.JsDom.TypedTag

case object BootstrapExtViewPresenter extends DefaultViewPresenterFactory[BootstrapExtState.type](() => new BootstrapExtView)


class BootstrapExtView extends View {
  import JsDom.all._
  import scalacss.ScalatagsCss._
  import scalacss.Defaults._

  override def getTemplate: dom.Element = div(
    ResetGuideStyles.render[TypedTag[HTMLStyleElement]],
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
    p("The icons from ", i("Glyphicons"), " and ", i("FontAwesome"), " packages are accessible in ", i("Icons"), " object."),
    CodeBlock(
      s"""UdashBootstrap.loadFontAwesome(),
         |UdashButtonToolbar(
         |  UdashButtonGroup()(
         |    UdashButton()(Icons.Glyphicon.alignLeft).render,
         |    UdashButton()(Icons.Glyphicon.alignCenter).render,
         |    UdashButton()(Icons.Glyphicon.alignRight).render,
         |    UdashButton()(Icons.Glyphicon.alignJustify).render
         |  ).render,
         |  UdashButtonGroup()(
         |    UdashButton()(Icons.FontAwesome.bitcoin).render,
         |    UdashButton()(Icons.FontAwesome.euro).render,
         |    UdashButton()(Icons.FontAwesome.dollar).render
         |  ).render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.icons(),
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
          |  case ev => clicks.append(ev.source.render.textContent)
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
         |val options = SeqProperty[CheckboxModel](
         |  DefaultCheckboxModel("Checkbox 1 (pre-checked)", true),
         |  DefaultCheckboxModel("Checkbox 2", false),
         |  DefaultCheckboxModel("Checkbox 3", false)
         |)
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
         |val options = SeqProperty[CheckboxModel](
         |  DefaultCheckboxModel("Radio 1 (preselected)", true),
         |  DefaultCheckboxModel("Radio 2", false),
         |  DefaultCheckboxModel("Radio 3", false)
         |)
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
      s"""val items = SeqProperty[DefaultDropdownItem](
         |  UdashDropdown.DropdownHeader("Start"),
         |  UdashDropdown.DropdownLink("Intro", Url("#")),
         |  UdashDropdown.DropdownDisabled(UdashDropdown.DropdownLink("Test Disabled", Url("#"))),
         |)
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
    p(
      i("UdashInputGroup"), " groups input elements into one component. It also provides convinient methods for creating the elements structure: ",
      i("input"), " for wrapping input elements, ", i("addon"), " for text elements and ", i("buttons"), " for buttons."
    ),
    CodeBlock(
      s"""val vanityUrl = Property[String]
         |val buttonDisabled = Property(true)
         |vanityUrl.listen(v => buttonDisabled.set(v.isEmpty))
         |div(
         |  label("Your URL"),
         |  UdashInputGroup(InputGroupSize.Large)(
         |    UdashInputGroup.addon("https://example.com/users/", bind(vanityUrl)),
         |    UdashInputGroup.input(TextInput.debounced(vanityUrl).render),
         |    UdashInputGroup.buttons(
         |      UdashButton(
         |        disabled = buttonDisabled
         |      )("Go!").render,
         |      UdashButton()(
         |        "Clear",
         |        onclick :+= ((_: Event) => { vanityUrl.set(""); false })
         |      ).render
         |    )
         |  ).render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.inputGroups(),
    h3("Forms"),
    p(i("UdashForm"), " provides a lot of convinient methods for creating forms."),
    CodeBlock(
      s"""/** Omitting: ShirtSize, shirtSizeToLabe, labelToShirtSize */
         |trait UserModel {
         |  def name: String
         |  def age: Int
         |  def shirtSize: ShirtSize
         |}
         |
         |val user = ModelProperty[UserModel]
         |user.subProp(_.name).set("")
         |user.subProp(_.age).set(25)
         |user.subProp(_.shirtSize).set(Medium)
         |user.subProp(_.age).addValidator(new Validator[Int] {
         |  def apply(element: Int)(implicit ec: ExecutionContext) =
         |    Future {
         |      if (element <= 0) Invalid(Seq("It should be positive integer!"))
         |      else Valid
         |    }
         |})
         |
         |div(
         |  UdashForm(
         |    UdashForm.textInput()("User name")(user.subProp(_.name)),
         |    UdashForm.numberInput(
         |      validation = Some(UdashForm.validation(user.subProp(_.age)))
         |    )("Age")(user.subProp(_.age).transform(_.toString, _.toInt)),
         |    UdashForm.group(
         |      label("Shirt size"),
         |      UdashForm.radio(
         |        user.subProp(_.shirtSize)
         |          .transform(shirtSizeToLabel, labelToShirtSize),
         |        Seq(Small, Medium, Large).map(shirtSizeToLabel),
         |        radioStyle = BootstrapStyles.Form.radioInline
         |      )
         |    ),
         |    UdashForm.disabled()(UdashButton()("Send").render)
         |  ).render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.simpleForm(),
    p("It is also possible to create ", i("inline"), " or ", i("horizontal"), " forms."),
    CodeBlock(
      s"""val search = Property[String]
         |div(
         |  UdashForm.inline(
         |    UdashForm.group(
         |      UdashInputGroup()(
         |        UdashInputGroup.addon("Search: "),
         |        UdashInputGroup.input(TextInput.debounced(search).render)
         |      ).render
         |    ),
         |    UdashButton()("Send").render
         |  ).render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.inlineForm(),
    h3("Navs"),
    CodeBlock(
      s"""trait Panel {
         |  def title: String
         |  def content: String
         |}
         |case class DefaultPanel(override val title: String,
         |                        override val content: String) extends Panel
         |
         |val panels = SeqProperty[Panel](
         |  DefaultPanel("Title 1", "Content of panel 1..."),
         |  DefaultPanel("Title 2", "Content of panel 2..."),
         |  DefaultPanel("Title 3", "Content of panel 3..."),
         |  DefaultPanel("Title 4", "Content of panel 4...")
         |)
         |val selected = Property[Panel](panels.elemProperties.head.get)
         |panels.append(DefaultPanel("Title 5", "Content of panel 5..."))
         |
         |div(StyleUtils.center, GuideStyles.frame, ResetGuideStyles.reset)(
         |  UdashNav.tabs(justified = true)(panels)(
         |    elemFactory = (panel) => a(href := "", onclick :+= ((ev: Event) => {
         |      selected.set(panel.get)
         |      true
         |    }))(bind(panel.asModel.subProp(_.title))).render,
         |    isActive = (panel) => panel.combine(selected)(
         |      (panel, selected) => panel.title == selected.title
         |    )
         |  ).render,
         |  div(BootstrapStyles.Well.well)(
         |    bind(selected.asModel.subProp(_.content))
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.navs(),
    h3("Navbar"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Breadcrumbs"),
    CodeBlock(
      s"""
         |import io.udash.bootstrap.utils.UdashBreadcrumbs._
         |
         |val pages = SeqProperty[Breadcrumb](
         |  DefaultBreadcrumb("Udash", Url("http://udash.io/")),
         |  DefaultBreadcrumb("Dev's Guide", Url("http://guide.udash.io/")),
         |  DefaultBreadcrumb("Extensions", Url("http://guide.udash.io/")),
         |  DefaultBreadcrumb("Bootstrap wrapper", Url("http://guide.udash.io/ext/bootstrap"))
         |)
         |val selected = pages.transform((pages: Seq[_]) => pages.size - 1)
         |val breadcrumbs = UdashBreadcrumbs(pages, selected)(defaultPageFactory)
         |breadcrumbs.render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.breadcrumbs(),
    h3("Pagination"),
    CodeBlock(
      s"""import UdashPagination._
         |import Context._
         |
         |val showArrows = Property(true)
         |val highlightActive = Property(true)
         |val toggleArrows = UdashButton.toggle(active = showArrows)("Toggle arrows")
         |val toggleHighlight = UdashButton.toggle(active = highlightActive)("Toggle highlight")
         |
         |val pages = SeqProperty(Seq.tabulate[Page](7)(idx =>
         |  DefaultPage((idx+1).toString, Url(applicationInstance.currentState.url))
         |))
         |val selected = Property(0)
         |val pagination = UdashPagination(
         |  showArrows = showArrows, highlightActive = highlightActive
         |)(pages, selected)(defaultPageFactory)
         |val pager = UdashPagination.pager()(pages, selected)(defaultPageFactory)
         |div(StyleUtils.center, GuideStyles.frame)(
         |  div("Selected page index: ", bind(selected)),
         |  div(
         |    UdashButtonGroup()(
         |      toggleArrows.render,
         |      toggleHighlight.render
         |    ).render
         |  ),
         |  pagination.render,
         |  pager.render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.pagination(),
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
    h3("Alerts"),
    p("The ", i("UdashAlert")," component supports both regular and dismissible Bootstrap alerts with typesafe styling and ",
    i("Property"),"-based dismissal mechanism."),
    CodeBlock(
      s"""|val styles = Seq(AlertStyle.Info, AlertStyle.Danger,
         |  AlertStyle.Success, AlertStyle.Warning)
         |val dismissed = SeqProperty[String](Seq.empty)
         |
         |def randomDismissible(): dom.Element = {
         |  val title = randomString()
         |  val alert = UdashAlert.dismissible(
         |    styles(Random.nextInt(styles.size))
         |  )(title)
         |  alert.dismissed.listen(_ => dismissed.append(title))
         |  alert.render
         |}
         |
         |val alerts = div(BootstrapStyles.Well.well)(
         |  UdashAlert.info("info").render,
         |  UdashAlert.success("success").render,
         |  UdashAlert.warning("warning").render,
         |  UdashAlert.danger("danger").render
         |).render
         |
         |val create = UdashButton(
         |  size = ButtonSize.Large
         |)("Create dismissible alert")
         |
         |create.listen { case _ => alerts.appendChild(randomDismissible()) }
         |div(StyleUtils.center)(
         |  create.render,
         |  alerts,
         |  h4("Dismissed: "),
         |  produce(dismissed)(seq =>
         |    ul(BootstrapStyles.Well.well)(seq.map(click =>
         |      li(click)
         |    ): _*).render
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.alerts(),
    h3("Progress bars"),
    p("The ", i("UdashProgressBar"), " component provides a simple way to use built-in Bootstrap progress bars ",
      "with custom stringifiers and ", i("Property"), "-controlled value, percentage showing and animation."),
    CodeBlock(
      s"""|val showPercentage = Property(true)
          |val animate = Property(true)
          |val value = Property(50)
          |div(StyleUtils.center, GuideStyles.frame)(
          |  div(
          |    UdashButtonGroup()(
          |      UdashButton.toggle(active = showPercentage)("Show percentage").render,
          |      UdashButton.toggle(active = animate)("Animate").render
          |    ).render
          |  ), br,
          |  UdashProgressBar(value, showPercentage, Success)().render,
          |  UdashProgressBar(value, showPercentage, Striped)(value => value+" percent").render,
          |  UdashProgressBar.animated(value, showPercentage, animate, Danger)().render,
          |  NumberInput.debounced(value.transform(_.toString, Integer.parseInt))(
          |    BootstrapStyles.Form.formControl, placeholder := "Percentage"
          |  )
          |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.progressBar(),
    h3("Media object"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("List group"),
    CodeBlock(
      s"""import io.udash.bootstrap.BootstrapImplicits._
         |val news = SeqProperty[String]("Title 1", "Title 2", "Title 3")
         |val listGroup = UdashListGroup(news)((news) =>
         |  li(
         |    BootstrapStyles.active.styleIf(news.transform(_.endsWith("1"))),
         |    BootstrapStyles.disabled.styleIf(news.transform(_.endsWith("2"))),
         |    BootstrapStyles.List.listItemSuccess.styleIf(news.transform(_.endsWith("3"))),
         |    BootstrapStyles.List.listItemDanger.styleIf(news.transform(_.endsWith("4"))),
         |    BootstrapStyles.List.listItemInfo.styleIf(news.transform(_.endsWith("5"))),
         |    BootstrapStyles.List.listItemWarning.styleIf(news.transform(_.endsWith("6")))
         |  )(bind(news)).render
         |)
         |
         |var i = 1
         |val appendHandler = window.setInterval(() => {
         |  news.append(s"Dynamic $i")
         |  i += 1
         |}, 2000)
         |window.setTimeout(() => window.clearInterval(appendHandler), 20000)
         |
         |div(StyleUtils.center, GuideStyles.frame)(
         |  div(ResetGuideStyles.reset)(
         |    listGroup.render
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.listGroup(),
    h3("Panels"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Responsive embed"),
    CodeBlock(
      s"""div(
         |  div(BootstrapStyles.EmbedResponsive.embed,
         |      BootstrapStyles.EmbedResponsive.embed16by9)(
         |    iframe(BootstrapStyles.EmbedResponsive.item, src := "...")
         |  ),
         |  div(BootstrapStyles.EmbedResponsive.embed,
         |      BootstrapStyles.EmbedResponsive.embed4by3)(
         |    iframe(BootstrapStyles.EmbedResponsive.item, src := "...")
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.responsiveEmbed(),
    h3("Wells"),
    CodeBlock(
      s"""div(
         |  div(BootstrapStyles.Well.well, BootstrapStyles.Well.wellSm)(
         |    "Small well..."
         |  ),
         |  div(BootstrapStyles.Well.well)(
         |    "Standard well..."
         |  ),
         |  div(BootstrapStyles.Well.well, BootstrapStyles.Well.wellLg)(
         |    "Large well..."
         |  )
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.wells(),
    h3("Modals"),
    p(
      "The modal window constructor takes three optional methods as the arguments. The first one is used to create ",
      "a modal window's header, the second creates a body and the last produces a window's footer."
    ),
    p(
      "The ", i("UdashModal"), " class exposes methods for opening/hiding window. It is also possible to listen on window's events."
    ),
    CodeBlock(
      s"""val events = SeqProperty[UdashModal.ModalEvent]
         |
         |val header = () => div(
         |  "Modal events",
         |  UdashButton()(
         |    UdashModal.CloseButtonAttr,
         |    BootstrapStyles.close, "×"
         |  ).render
         |).render
         |val body = () => div(
         |  div(BootstrapStyles.Well.well)(
         |    ul(repeat(events)(event => li(event.get.toString).render))
         |  )
         |).render
         |val footer = () => div(
         |  UdashButton()(UdashModal.CloseButtonAttr, "Close").render,
         |  UdashButton(style = ButtonStyle.Primary)("Something...").render
         |).render
         |
         |val modal = UdashModal(modalSize = ModalSize.Large)(
         |  headerFactory = Some(header),
         |  bodyFactory = Some(body),
         |  footerFactory = Some(footer)
         |)
         |modal.listen { case ev => events.append(ev) }
         |
         |val openModalButton = UdashButton(style = ButtonStyle.Primary)(
         |  modal.openButtonAttrs(), "Show modal..."
         |)
         |val openAndCloseButton = UdashButton()(
         |  "Open and close after 2 seconds...",
         |  onclick :+= ((_: Event) => {
         |    modal.show()
         |    window.setTimeout(() => modal.hide(), 2000)
         |    false
         |  }))
         |
         |div(
         |  modal.render,
         |  UdashButtonGroup()(
         |    openModalButton.render,
         |    openAndCloseButton.render
         |  ).render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.simpleModal(),
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
    p(
      i("UdashCollapse"), " represents element with toggle behaviour. It provides methods ",
      i("toggle"), ", ", i("open"), " and ", i("close"), " for manual manipulation and ",
      i("toggleButtonAttrs"), " for easy creation of toggle button."
    ),
    CodeBlock(
      s"""val events = SeqProperty[UdashCollapse.CollapseEvent]
         |
         |val collapse = UdashCollapse()(
         |  div(BootstrapStyles.Well.well)(
         |    ul(repeat(events)(event => li(event.get.toString).render))
         |  )
         |)
         |collapse.listen { case ev => events.append(ev) }
         |
         |val toggleButton = UdashButton(style = ButtonStyle.Primary)(
         |  collapse.toggleButtonAttrs(), "Toggle..."
         |)
         |val openAndCloseButton = UdashButton()(
         |  "Open and close after 2 seconds...",
         |  onclick :+= ((_: Event) => {
         |    collapse.show()
         |    window.setTimeout(() => collapse.hide(), 2000)
         |    false
         |  })
         |)
         |
         |div(
         |  UdashButtonGroup(justified = true)(
         |    toggleButton.render,
         |    openAndCloseButton.render
         |  ).render,
         |  collapse.render
         |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.simpleCollapse(),
    p(
      i("UdashAccordion"), " internally uses ", i("UdashCollapse"), ". It provides ",
      i("collapseOf"), " method for obtaining ", i("UdashCollapse"), " created for selected element."
    ),
    CodeBlock(
      s"""val events = SeqProperty[UdashCollapse.CollapseEvent]
         |val news = SeqProperty[String](
         |  "Title 1", "Title 2", "Title 3"
         |)
         |
         |val accordion = UdashAccordion(news)(
         |  (news) => span(news.get).render,
         |  (_) => div(BootstrapStyles.Panel.panelBody)(
         |    div(BootstrapStyles.Well.well)(
         |      ul(repeat(events)(event => li(event.get.toString).render))
         |    )
         |  ).render
         |)
         |
         |val accordionElement = accordion.render
         |news.elemProperties.map(news => {
         |  accordion.collapseOf(news)
         |}).filter(_.isDefined)
         |  .foreach(_.get.listen { case ev => events.append(ev) })
         |
         |div(StyleUtils.center, GuideStyles.frame)(
         |  div(ResetGuideStyles.reset)(
         |    accordionElement
         |  )
         |).render
       """.stripMargin
    )(GuideStyles),
    BootstrapDemos.accordionCollapse(),
    h3("Carousel"),
    p(
      i("UdashCarousel"), " is a slideshow component. It exposes its state (slides, current view) through ", i("Properties"),
      " and can be cycled through programatically."
    ),
    CodeBlock(
      s"""|def newSlide(): UdashCarouselSlide = UdashCarouselSlide(
          |  Url("assets/images/ext/bootstrap/carousel.png")
          |)(
          |  h3(randomString()),
          |  p(randomString())
          |)
          |val slides = SeqProperty[UdashCarouselSlide](
          |  (1 to 4).map(_ => newSlide())
          |)
          |val active = Property(false)
          |import scala.concurrent.duration._
          |val carousel = UdashCarousel(slides, activeSlide = 1,
          |  animationOptions = AnimationOptions(interval = 2 seconds,
          |    keyboard = false, active = active.get)
          |)
          |val prevButton = UdashButton()("Prev")
          |val nextButton = UdashButton()("Next")
          |val prependButton = UdashButton()("Prepend")
          |val appendButton = UdashButton()("Append")
          |carousel.listen { case any => println(any) }
          |prevButton.listen { case _ => carousel.previousSlide() }
          |nextButton.listen { case _ => carousel.nextSlide() }
          |prependButton.listen { case _ => slides.prepend(newSlide()) }
          |appendButton.listen { case _ => slides.append(newSlide()) }
          |active.listen(b => if (b) carousel.cycle() else carousel.pause())
          |div(StyleUtils.center)(
          |  div(GuideStyles.frame)(
          |    UdashButtonToolbar(
          |      UdashButton.toggle(active = active)("Run animation").render,
          |      UdashButtonGroup()(
          |        prevButton.render,
          |        nextButton.render
          |      ).render,
          |      UdashButtonGroup()(
          |        prependButton.render,
          |        appendButton.render
          |      ).render
          |    ).render
          |  ),
          |  div(ResetGuideStyles.reset)(
          |    carousel.render
          |  ).render
          |).render""".stripMargin
    )(GuideStyles),
    BootstrapDemos.carousel(),
    h2("What's next?"),
    p("...")
  ).render

  override def renderChild(view: View): Unit = {}
}