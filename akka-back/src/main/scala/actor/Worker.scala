package org.nisshiee.akkaback

import akka.actor._

object Worker {

  case object Finish
}

class Worker extends Actor {

  import Worker._

  override def receive = {
    case s: String => {
      doWork(s)
      sender ! Finish
    }
  }

  def doWork(s: String): Unit = {
    println(s"start work: $s")
    Thread.sleep(1000)
    println(s"finish work: $s")
  }
}
