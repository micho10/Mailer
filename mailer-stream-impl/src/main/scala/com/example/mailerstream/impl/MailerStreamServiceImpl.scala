package com.example.mailerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.example.mailerstream.api.MailerStreamService
import com.example.mailer.api.MailerService

import scala.concurrent.Future

/**
  * Implementation of the MailerStreamService.
  */
class MailerStreamServiceImpl(mailerService: MailerService) extends MailerStreamService {
  def stream = ServiceCall { hellos =>
    Future.successful(hellos.mapAsync(8)(mailerService.sendHelloEmail(_).invoke()))
  }
}
