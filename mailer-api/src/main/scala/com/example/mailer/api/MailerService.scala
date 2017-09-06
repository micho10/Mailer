package com.example.mailer.api

import akka.NotUsed
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * The Mailer service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and consume the [[MailerService]]. It defines how the
  * service is invoked and implemented. It also defines the metadata that describes how the interface is mapped down onto an
  * underlying transport protocol.
  *
  * The service descriptor, its implementation and consumption should remain agnostic to what transport is being used.
  */
trait MailerService extends Service {

  /**
    * Example: curl http://localhost:9000/api/email/Alice
    *
    * [[ServiceCall]] takes two type parameters: Request and Response. The Request parameter is the type of the incoming
    * request message, and the Response parameter is the type of the outgoing response message.
    *
    * @return a handle to the call which can be invoked using the [[invoke()]] method.
    */
  def sendHelloEmail(subject: String): ServiceCall[NotUsed, String]

  /**
    *
    * @param subject
    * @param from
    * @param to
    * @param bodyText
    * @param bodyHtml
    * @param charset
    * @param cc
    * @param bcc
    */
  def sendEmail(subject: String, from: String, to: String, bodyText: String, bodyHtml: String, charset: String,
                cc: String, bcc: String): ServiceCall[NotUsed, String]

  /**
    * It defines the service name and the REST endpoints it offers.
    *
    * For each endpoint, declare an abstract method in the service interface.
    *
    * @return a service Descriptor
    */
  override final def descriptor: Descriptor = {
    import Service.{named, pathCall}
    named("mailer").withCalls(
      pathCall("/api/email/:subject", sendHelloEmail _),
      pathCall("/api/email/send?subject&from&to&bodyText&bodyHtml&cc&bcc", sendEmail _)
    ).withAutoAcl(true)   // Generate service ACLs from each call's path pattern
  }
}



final case class MailContent(subject: String, to: String, body: String)

