package com.example.mailer.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import com.example.mailer.api.MailerService

/**
  * Implementation of the MailerService.
  */
class MailerServiceImpl(persistentEntityRegistry: PersistentEntityRegistry) extends MailerService {

  override def hello(id: String) = ServiceCall { _ =>
    // Look up the Mailer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailerEntity](id)

    // Ask the entity the Hello command.
    ref.ask(Hello(id, None))
  }

  override def useGreeting(id: String) = ServiceCall { request =>
    // Look up the Mailer entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailerEntity](id)

    // Tell the entity to use the greeting message specified.
    ref.ask(UseGreetingMessage(request.message))
  }
}
