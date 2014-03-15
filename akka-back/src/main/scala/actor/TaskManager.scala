package org.nisshiee.akkaback

import akka.actor._

object TaskManager {
  case object GetStatus
}

class TaskManager extends Actor {

  import TaskManager._

  val worker = context.actorOf(Props[Worker])

  var allCount: Long = 0l
  var finishCount: Long = 0l

  override def receive = {
    case s: String => {
      allCount = allCount + 1l
      worker ! s
    }
    case Worker.Finish => {
      finishCount = finishCount + 1l
    }
    case GetStatus => sender ! (finishCount -> allCount)
  }
}
