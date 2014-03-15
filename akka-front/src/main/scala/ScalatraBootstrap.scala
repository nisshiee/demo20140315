import skinny._
import skinny.controller._
import _root_.controller._

import org.nisshiee.akkafront._

class ScalatraBootstrap extends SkinnyLifeCycle {

  override def initSkinnyApp(ctx: ServletContext) {
    Controllers.root.mount(ctx)
    AssetsController.mount(ctx)
  }

  override def destroy(ctx: ServletContext) {
    AkkaService.shutdown
    super.destroy(ctx)
  }
}

