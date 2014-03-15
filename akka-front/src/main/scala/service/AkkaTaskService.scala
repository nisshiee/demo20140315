package org.nisshiee.akkafront

import scala.concurrent._, duration._

import akka.actor._
import akka.pattern.ask
import akka.util.Timeout

import com.typesafe.config.ConfigFactory

object AkkaTaskService {

  import AkkaService._
  implicit val timeout = Timeout(5 second)

  val actor = system.actorSelection(ConfigFactory.load.getString("akkafront.back-url"))

  def status: Future[(Long, Long)] = {
    actor ? "status" map { case t: (Long, Long) => t }
  }

  def sendtask(list: List[String]): Unit = {
    actor ! list
  }
}
