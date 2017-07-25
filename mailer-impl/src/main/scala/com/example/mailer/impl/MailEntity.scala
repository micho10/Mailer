package com.example.mailer.impl

import akka.persistence.PersistentActor
import com.example.mailer.api.{MailEntity, MailId}

class MailEntity extends PersistentActor {

  import MailEntity._
  import context._

  private var state = MailState()

  override def persistenceId: String = "email"

  override def receiveCommand: Receive = {
    case GetMail(id)             => sender() ! state(id)
    case CreateMail(content)     =>
      handleEvent(MailCreated(MailId, content)) pipeTo sender()
      ()
    case UpdateMail(id, content) =>
      state(id) match {
        case response @ Left(_) => sender() ! response
        case Right(_)           =>
          handleEvent(MailUpdated(id, content)) pipeTo sender()
          ()
      }
  }

  private def handleEvent[E <: MailEvent](e: => E): Future[E]

}
