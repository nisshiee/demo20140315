package controller

import skinny._
import org.nisshiee.akkafront._

class RootController extends ApplicationController {

  def index = {
    set("hello", HelloAkkaService.hello)
    render("/root/index")
  }

}

