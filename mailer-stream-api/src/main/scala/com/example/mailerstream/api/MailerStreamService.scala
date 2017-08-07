package com.example.mailerstream.api

import akka.NotUsed
import akka.stream.scaladsl.Source
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}

/**
  * The Mailer stream interface.
  *
  * This describes everything that Lagom needs to know about how to serve and consume the MailerStream service.
  */
trait MailerStreamService extends Service {

  /**
    * Example: curl http://localhost:9000/api/email/Alice
    *
    * { @code ServiceCall} takes two type parameters: Request and Response. The Request parameter is the type of the
    * incoming request message, and the Response parameter is the type of the outgoing response message.
    *
    * Lagom will choose an appropriate transport for the stream, typically, this will be WebSockets.
    *
    * @return a handle to the call which can be invoked using the { @code invoke()} method.
    */
  def stream: ServiceCall[Source[String, NotUsed], Source[String, NotUsed]]

  /**
    * It defines the service name and the REST endpoints it offers.
    *
    * For each endpoint, declare an abstract method in the service interface.
    *
    * @return a service Descriptor
    */
  override final def descriptor: Descriptor = {
    import Service._

    named("mailer-stream")
      .withCalls(
        namedCall("stream", stream)
      ).withAutoAcl(true)
  }

}
