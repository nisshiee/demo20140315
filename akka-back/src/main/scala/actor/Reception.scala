package org.nisshiee.akkaback

import scala.concurrent.duration._
import akka.actor._
import akka.pattern.{ ask, pipe }
import akka.util.Timeout

class Reception extends Actor {

  implicit val dispatcher = context.system.dispatcher
  implicit val timeout = Timeout(5 second)

  val taskManager = context.actorOf(Props[TaskManager])

  override def receive = {
    case "hello" => sender ! "hello"
    case "status" => taskManager ? TaskManager.GetStatus pipeTo sender
    case l: List[_] => l foreach {
      case s: String => taskManager ! s
      case _ => ()
    }
  }
}
