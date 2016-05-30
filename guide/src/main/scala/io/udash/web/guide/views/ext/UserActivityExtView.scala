package io.udash.web.guide.views.ext

import io.udash.bindings.Checkbox
import io.udash._
import io.udash.core.Presenter
import io.udash.web.commons.components.CodeBlock
import io.udash.web.guide.demos.activity.Call
import io.udash.web.guide.styles.BootstrapStyles
import io.udash.web.guide.styles.partials.GuideStyles
import io.udash.web.guide.views.ext.demo.UrlLoggingDemo
import io.udash.web.guide.views.rpc.demos.PingPongCallDemoComponent
import io.udash.web.guide.{Context, UserActivityExtState}
import org.scalajs.dom
import org.scalajs.dom._

import scala.util.{Failure, Success}
import scalatags.JsDom

class UserActivityExtPresenter(model: SeqProperty[Call]) extends Presenter[UserActivityExtState.type] with StrictLogging {

  import io.udash.web.guide.Context._

  override def handleState(state: UserActivityExtState.type): Unit = {}

  def reload(): Unit = {
    Context.serverRpc.demos().call().calls.onComplete {
      case Success(calls) => model.set(calls)
      case Failure(t) => logger.error(t.getMessage)
    }
  }
}


case object UserActivityExtViewPresenter extends ViewPresenter[UserActivityExtState.type] {

  import io.udash.web.guide.Context._

  override def create(): (View, Presenter[UserActivityExtState.type]) = {
    val model = SeqProperty[Call]
    val presenter = new UserActivityExtPresenter(model)
    (new UserActivityExtView(model, presenter), presenter)
  }
}

class UserActivityExtView(model: SeqProperty[Call], presenter: UserActivityExtPresenter) extends View {

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
    span(GuideStyles.frame)(
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
    ),
    h2("RPC call logging"),
    p("Enabling backend call logging is also quite simple. In order to define logging behaviour, you have to mix ",
      "CallLogging into your ExposesServerRPC, e.g.:"),
    CodeBlock(
      """ new DefaultExposesServerRPC[MainServerRPC](new ExposedRpcInterfaces(clientId)) with CallLogging[MainServerRPC] {
        |  override protected val metadata: RPCMetadata[MainServerRPC] = RPCMetadata[MainServerRPC]
        |
        |  override def log(rpcName: String, methodName: String, args: Seq[String]): Unit =
        |    println(s"$rpcName $methodName $args")
        |} """.stripMargin)(GuideStyles),
    p("The methods you want log calls on have to be annotated with ", i("@Logged"), ". For this example we reused the ping example from RPC guide introduction:"),
    CodeBlock(
      """import com.avsystem.commons.rpc.RPC
        |import io.udash.rpc.utils.Logged
        |
        |@RPC
        |trait PingPongServerRPC {
        |
        |  @Logged
        |  def fPing(id: Int): Future[Int]
        |}""".stripMargin)(GuideStyles),
    new PingPongCallDemoComponent,
    span(GuideStyles.frame)(
      button(id := "call-logging-demo", BootstrapStyles.btn, BootstrapStyles.btnPrimary)
      (onclick :+= ((_: MouseEvent) => {
        presenter.reload();
        true
      }))("Load call list"),
      produce(model)(seq =>
        ul(
          seq.map(call => li(call.toString)): _*
        ).render
      )
    )
  ).render

  override def renderChild(view: View): Unit = {

  }
}