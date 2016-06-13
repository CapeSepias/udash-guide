package io.udash.web.guide.views.ext

import io.udash._
import io.udash.bootstrap.BootstrapStyles
import io.udash.web.commons.components.CodeBlock
import io.udash.web.guide._
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.views.{References, Versions}
import org.scalajs.dom

import scalatags.JsDom

case object BootstrapExtViewPresenter extends DefaultViewPresenterFactory[BootstrapExtState.type](() => new BootstrapExtView)


class BootstrapExtView extends View {
  import JsDom.all._
  import scalacss.ScalatagsCss._

  override def getTemplate: dom.Element = div(
    h1("Udash Bootstrap wrapper"),
    p("..."),
    h2("The first steps"),
    p("To start development with the Bootstrap wrapper add the following line in you frontend module dependencies: "),
    CodeBlock(
      s""""io.udash" %%% "udash-bootstrap" % "${Versions.udashVersion}"""".stripMargin
    )(GuideStyles),
    p("The wrapper provides a typed equivalent of the ", a(href := References.bootstrapHomepage)("Twitter Bootstrap"), " API."),
    h2("Statics"),
    p(s"All Bootstrap tags and styles are available as ScalaCSS applicable styles (", i("StyleA"), ")"),
    CodeBlock(
      s"""|div(BootstrapStyles.row)(
          |  div(BootstrapStyles.Grid.colXs9, BootstrapStyles.Well.well)(".col-xs-9"),
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
    div(BootstrapStyles.row)(
      div(BootstrapStyles.Grid.colXs9, BootstrapStyles.Well.well)(".col-xs-9"),
      div(BootstrapStyles.Grid.colXs4, BootstrapStyles.Well.well)(
        ".col-xs-4", br,
        "Since 9 + 4 = 13 > 12, this 4-column-wide div",
        "gets wrapped onto a new line as one contiguous unit."
      ),
      div(BootstrapStyles.Grid.colXs6, BootstrapStyles.Well.well)(
        ".col-xs-6", br,
        "Subsequent columns continue along the new line."
      )
    ),
    h2("Components"),
    p("..."),
    h3("Glyphicons & FontAwesome"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Dropdowns"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Button"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Button groups"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Button dropdowns"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
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
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Badges"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Jumbotron"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
    )(GuideStyles),
    h3("Page header"),
    p("..."),
    CodeBlock(
      s"""???""".stripMargin
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