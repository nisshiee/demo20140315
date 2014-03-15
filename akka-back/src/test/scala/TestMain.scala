package org.nisshiee.akkaback

import akka.actor._

object TestMain {

  def main(args: Array[String]): Unit = {

    val system = ActorSystem("akka-back")
    val reception = system.actorOf(Props[Reception], "reception")

    readLine

    system.shutdown
  }
}

