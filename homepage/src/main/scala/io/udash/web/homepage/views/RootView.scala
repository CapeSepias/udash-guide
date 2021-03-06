package io.udash.web.homepage.views

import io.udash._
import io.udash.web.commons.components.Footer
import io.udash.web.commons.styles.GlobalStyles
import io.udash.web.commons.styles.components.FooterStyles
import io.udash.web.homepage.RootState
import io.udash.web.homepage.components.Header
import io.udash.web.homepage.styles.HomepageDefaultStyles
import io.udash.web.homepage.styles.partials._
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLStyleElement

import scala.scalajs.js
import scalatags.JsDom.TypedTag
import scalatags.JsDom.tags2._

object RootViewPresenter extends DefaultViewPresenterFactory[RootState.type](() => new RootView)

class RootView extends View {
  import scalacss.Defaults._
  import scalacss.ScalatagsCss._
  import scalatags.JsDom.all._

  private var child: Element = div().render

  private val content = div(
    HomepageDefaultStyles.render[TypedTag[HTMLStyleElement]],
    GlobalStyles.render[TypedTag[HTMLStyleElement]],
    HomepageStyles.render[TypedTag[HTMLStyleElement]],
    FooterStyles.render[TypedTag[HTMLStyleElement]],
    HeaderStyles.render[TypedTag[HTMLStyleElement]],
    ButtonsStyle.render[TypedTag[HTMLStyleElement]],
    DemoStyles.render[TypedTag[HTMLStyleElement]],

    Header.getTemplate,
    main(GlobalStyles.main)(
      child
    ),
    Footer.getTemplate
  ).render

  override def getTemplate: Element = content

  override def renderChild(view: View): Unit = {
    import io.udash.wrappers.jquery._
    val newChild = view.getTemplate
    jQ(child).replaceWith(newChild)
    child = newChild

    js.Dynamic.global.svg4everybody()
  }
}