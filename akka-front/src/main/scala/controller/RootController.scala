package controller

import skinny._
import org.nisshiee.akkafront._

class RootController extends ApplicationController {

  def index = {
    set("status", AkkaTaskService.status)
    render("/root/index")
  }

  def sendtask = {
    params.getAs[String]("tasks") foreach { s =>
      val list = s.split("""[\r\n]+""").toList
      AkkaTaskService.sendtask(list)
    }
    redirect("/")
  }

  def stop = {
    AkkaTaskService.stop
    redirect("/")
  }
}
