package org.nisshiee.akkaback

import akka.actor._
import akka.dispatch._
import com.typesafe.config.Config

sealed trait InterruptibleMessageQueue extends MessageQueue

case object Interruptible {
  case object Reset
  case object Restart
}

class InterruptibleMailbox(val settings: ActorSystem.Settings, val config: Config)
extends MailboxType with ProducesMessageQueue[InterruptibleMessageQueue] {

  val underlyingMailboxType = new UnboundedMailbox(settings, config)

  final override def create(owner: Option[ActorRef], system: Option[ActorSystem]): MessageQueue =
    new InterruptibleMessageQueue {

      val regular = underlyingMailboxType.create(owner, system)
      val reset = underlyingMailboxType.create(owner, system)

      override def cleanUp(owner: ActorRef, deadLetters: MessageQueue): Unit = {
        regular.cleanUp(owner, deadLetters)
        reset.cleanUp(owner, deadLetters)
      }

      override def dequeue(): Envelope =
        if (reset.hasMessages) reset.dequeue
        else if (regular.hasMessages) regular.dequeue
        else null

      override def enqueue(receiver: ActorRef, handle: Envelope): Unit = handle.message match {
        case Interruptible.Reset => reset.enqueue(receiver, handle)
        case _ => regular.enqueue(receiver, handle)
      }

      override def hasMessages: Boolean = regular.hasMessages || reset.hasMessages

      override def numberOfMessages: Int = regular.numberOfMessages + reset.numberOfMessages
    }
}
