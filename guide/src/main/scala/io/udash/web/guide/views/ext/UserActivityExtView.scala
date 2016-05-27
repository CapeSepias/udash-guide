package io.udash.web.guide.views.ext

import io.udash._
import io.udash.bindings.Checkbox
import io.udash.core.DefaultViewPresenterFactory
import io.udash.web.commons.components.CodeBlock
import io.udash.web.guide.UserActivityExtState
import io.udash.web.guide.styles.BootstrapStyles
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.views.ext.demo.UrlLoggingDemo
import org.scalajs.dom

import scalatags.JsDom

case object UserActivityExtViewPresenter extends DefaultViewPresenterFactory[UserActivityExtState.type](() => new UserActivityExtView)

class UserActivityExtView extends View {

  import io.udash.web.guide.Context._

  import JsDom.all._
  import scalacss.ScalatagsCss._

  override def getTemplate: dom.Element = div(
    h1("Udash user activity monitoring"),
    p(
      """When it comes to website tracking there are a plethora of metrics at our disposal.
        |If we’re talking user engagement, we might look at the bounce rate, average time on site, or average page views per visit.
        |For organic search, we might look at the number of organic visits, top organic keywords, and others.
        |Then there’s content — the king of all data. Which web pages are the most popular?
        |What are the most common navigation flows? Which features are most commonly used?
        |Udash user activity extenstions enable you to gather the data you need to provide the best user experience for your website.""".stripMargin
    ),
    h2("Browser navigation"),
    p("To enable browser navigation tracking, simply mixin UrlLogging into your frontend application.",
      "The ", i("log(url, referrer)"), " method will be called whenever the user changes app state."),
    p("You can see this mechanism in action here in the guide. We've already provided the implementation:"),
    CodeBlock(
      s"""implicit val applicationInstance = new Application[RoutingState](routingRegistry, viewPresenterRegistry, RootState) with UrlLogging[RoutingState] {
          |    override protected def log(url: String, referrer: Option[String]): Unit = UrlLoggingDemo.log(url, referrer)
          |}""".stripMargin
    )(GuideStyles),
    CodeBlock(
      s"""object UrlLoggingDemo {
          |  import io.udash.web.guide.Context._
          |
         |  val enabled = Property(false)
          |  val history = SeqProperty[(String, Option[String])](ListBuffer.empty)
          |  enabled.listen(b => if(!b) history.set(ListBuffer.empty))
          |
         |  def log(url: String, referrer: Option[String]): Unit =
          |    if(enabled.get) history.append((url, referrer))
          |
         |}""".stripMargin
    )(GuideStyles),
    p("to see it in action just enable logging below, switch to another chapter and come back here."), br,
    form(BootstrapStyles.containerFluid)(
      div(BootstrapStyles.row)(
        div(BootstrapStyles.colMd4)(
          div(BootstrapStyles.inputGroup)(
            div(BootstrapStyles.inputGroupAddon)("Turn on logging:"),
            div(BootstrapStyles.inputGroupAddon)(Checkbox(UrlLoggingDemo.enabled, cls := "checkbox-demo-a"))
          )
        )
      )
    ), br,
    form(BootstrapStyles.containerFluid)(
      div(BootstrapStyles.row)(
        div(BootstrapStyles.colMd4)(
          b("Url")
        ),
        div(BootstrapStyles.colMd4)(
          b("Referrer")
        )
      ),
      produce(UrlLoggingDemo.history)(seq =>
        div()(seq.map { case (url, refOpt) =>
          div(BootstrapStyles.row)(
            div(BootstrapStyles.colMd4)(
              url
            ),
            div(BootstrapStyles.colMd4)(
              refOpt
            )
          )
        }: _*).render
      )
    )
  ).render

  override def renderChild(view: View): Unit = {}
}
