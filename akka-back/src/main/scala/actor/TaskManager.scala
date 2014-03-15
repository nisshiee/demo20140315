package org.nisshiee.akkaback

import akka.actor._

object TaskManager {
  case object GetStatus
  case object Reset
}

class TaskManager extends Actor {

  import TaskManager._

  val worker = context.actorOf(Props[Worker])

  var allCount: Long = 0l
  var finishCount: Long = 0l

  override def receive =
    receiveTask orElse receiveFinish orElse receiveGetStatus orElse receiveReset

  def receiveTask: Receive = {
    case s: String => {
      allCount = allCount + 1l
      worker ! s
    }
  }

  def receiveFinish: Receive = {
    case Worker.Finish => {
      finishCount = finishCount + 1l
    }
  }

  def receiveGetStatus: Receive = {
    case GetStatus => sender ! (finishCount -> allCount)
  }

  def receiveReset: Receive = {
    case Reset => {
      worker ! Interruptible.Reset
      worker ! Interruptible.Restart
      allCount = 0l
      finishCount = 0l
      context.become(receiveTask orElse receiveGetStatus orElse receiveRestartCount)
    }
  }

  def receiveRestartCount: Receive = {
    case Worker.RestartCountup => context.unbecome
  }
}
