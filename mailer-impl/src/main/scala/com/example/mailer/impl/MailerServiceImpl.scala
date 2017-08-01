package com.example.mailer.impl

import java.util.UUID

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.example.mailer.api.{MailContent, MailerService}

/**
  * Implementation of the MailerService.
  */
class MailerServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends MailerService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the Mailer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the Mailer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }

  /**
    * Example: curl http://localhost:9000/api/helloEmail/Alice
    */
  override def sendHelloEmail(id: UUID): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    // Look up the Mail entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](id.toString)

    // Ask the entity the Hello command.
    ref.ask(HelloEmail(id.toString, None))
  }

}
