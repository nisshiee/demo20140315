package org.nisshiee.akkafront

import akka.actor._

object AkkaService {

  val system = ActorSystem("akkafront")

  implicit lazy val dispatcher = system.dispatcher

  def shutdown: Unit = system.shutdown
}
