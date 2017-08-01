package com.example.mailer.impl

import akka.NotUsed
import com.example.mailer.api.MailerService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence.PersistentEntityRegistry
import org.apache.commons.mail.EmailAttachment
import play.api.libs.mailer.{AttachmentData, Email}

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
  override def sendHelloEmail(subject: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    val cid = "1234"
    val email = Email(
      s"TEST $subject",
      "Mister FROM <test@glsign.com>",
      Seq("Miss TO <carlos.sainz@globalsign.com>"),
      // Adds attachment
      attachments = Seq(
//        AttachmentFile("favicon.png", new File(environment.classLoader.getResource("public/images/favicon.png").getPath), contentId = Some(cid)),
        AttachmentData("data.txt", "data".getBytes, "text/plain", Some("Simple data"), Some(EmailAttachment.INLINE))
      ),
      // Sends text, HTML or both ...
      bodyText = Some("A text message"),
      bodyHtml = Some(s"""<html><body><p>An <b>html</b> message with cid <img src="cid:$cid"></p></body></html>""")
    )
//    val id = mailer.send(email)

    // Look up the Mail entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](subject)

    // Ask the entity the Hello command.
    ref.ask(HelloEmail(subject, None))
  }

}
