package com.example.mailer.impl

import javax.inject.Inject

import akka.NotUsed
import com.example.mailer.api.MailerService
import com.lightbend.lagom.scaladsl.api.ServiceCall
import com.lightbend.lagom.scaladsl.persistence._
import play.api.libs.mailer.{Email, MailerClient}

/**
  * Implementation of the MailerService.
  *
  * @param persistentEntityRegistry a registry for all [[PersistentEntity]] classes, which  must be registered here with
  *                                 [[PersistentEntityRegistry#register]] at startup. Later, [[PersistentEntityRef]] can be
  *                                 retrieved with [[PersistentEntityRegistry#refFor]]. Commands are sent to a
  *                                 [[PersistentEntity]] using a `PersistentEntityRef`.
  */
//class MailerServiceImpl(mailerClient: MailerClient, persistentEntityRegistry: PersistentEntityRegistry) extends MailerService {
class MailerServiceImpl @Inject() (mailerClient: MailerClient)(persistentEntityRegistry: PersistentEntityRegistry) extends MailerService {

//  private val mailerClient: MailerClient = new MailerClient {
//    override def send(data: Email): String = ???
//  }

  /**
    * Example: curl http://localhost:9000/api/helloEmail/
    *
    * @param subject email's subject
    * @return        a handle to the call which can be invoked using the [[invoke()]] method.
    */
  override def sendHelloEmail(subject: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    val cid = "1234"
    val email = Email(
      s"TEST $subject",
      "Mister FROM <test@glsign.com>",
      Seq("Miss TO <carlos.sainz@globalsign.com>"),
//      // Adds attachment
//      attachments = Seq(
////        AttachmentFile("favicon.png", new File(environment.classLoader.getResource("public/images/favicon.png").getPath), contentId = Some(cid)),
//        AttachmentData("data.txt", "data".getBytes, "text/plain", Some("Simple data"), Some(EmailAttachment.INLINE))
//      ),
      // Sends text, HTML or both ...
      bodyText = Some("A text message"),
      bodyHtml = Some(s"""<html><body><p>An <b>html</b> message with cid <img src="cid:$cid"></p></body></html>""")
    )
//    val id = mailerClient.send(email)

    // Look up the Mail entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](subject)

    // Ask the entity the Hello command.
    ref.ask(HelloEmail(subject, None))
  }

  override def sendEmail(subject: String, from: String, to: String, bodyText: String, bodyHtml: String, charset: String,
                         cc: String, bcc: String): ServiceCall[NotUsed, String] = ServiceCall { _ =>
    val email = Email(
      subject,
      from,
      Seq(to),
      Some(bodyText),
      Some(bodyHtml),
      Some(charset),
      Seq(cc),
      Seq(bcc)
    )

//    val id = mailerClient.send(email)

    // Look up the Mail entity for the given ID.
    val ref = persistentEntityRegistry.refFor[MailEntity](subject)

    // Ask the entity the Hello command.
    ref.ask(HelloEmail(subject, None))
  }

//  subject: String,
//  from: String,
//  to: Seq[String] = Seq.empty,
//  bodyText: Option[String] = None,
//  bodyHtml: Option[String] = None,
//  charset: Option[String] = None,
//  cc: Seq[String] = Seq.empty,
//  bcc: Seq[String] = Seq.empty,
//  replyTo: Seq[String] = Seq.empty,
//  bounceAddress: Option[String] = None,
//  attachments: Seq[Attachment] = Seq.empty,
//  headers: Seq[(String, String)] = Seq.empty)

}
