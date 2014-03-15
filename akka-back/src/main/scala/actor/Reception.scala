package org.nisshiee.akkaback

import akka.actor._

class Reception extends Actor {

  override def receive = {
    case "hello" => sender ! "hello"
  }
}
