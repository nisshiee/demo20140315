package controller

import skinny._

object Controllers {
  object root extends RootController with Routes {
    val indexUrl = get("/?")(index).as('index)
    val sendtaskUrl = post("/sendtask/?")(sendtask).as('sendtask)
  }
}

