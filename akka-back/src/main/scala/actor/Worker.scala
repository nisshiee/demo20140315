package org.nisshiee.akkaback

import akka.actor._
import akka.dispatch._

object Worker {

  case object Finish
  case object RestartCountup
}

class Worker extends Actor with RequiresMessageQueue[InterruptibleMessageQueue] {

  import Worker._

  override def receive = {
    case s: String => {
      doWork(s)
      sender ! Finish
    }
    case Interruptible.Reset => context.become(reset)
  }

  def reset: Receive = {
    case Interruptible.Restart => {
      sender ! RestartCountup
      context.unbecome
    }
  }

  def doWork(s: String): Unit = {
    println(s"start work: $s")
    Thread.sleep(1000)
    println(s"finish work: $s")
  }
}
