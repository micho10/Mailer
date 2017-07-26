package com.example.mailer.api

import java.util.UUID

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

/**
  * The Mailer service interface.
  * <p>
  * This describes everything that Lagom needs to know about how to serve and consume the MailerService.
  */
trait MailerService extends Service {

  /**
    * Example: curl http://localhost:9000/api/hello/Alice
    */
  //TODO: to be removed
  def hello(id: String): ServiceCall[NotUsed, String]

  /**
    * Example: curl http://localhost:9000/api/email/Alice
    */
  def helloEmail(id: UUID): ServiceCall[NotUsed, String]

  /**
    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
    * "Hi"}' http://localhost:9000/api/hello/Alice
    */
  //TODO: to be removed
  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

  /**
    * It defines the service name and the REST endpoints it offers.
    *
    * For each endpoint, declare an abstract method in the service interface.
    *
    * @return a Descriptor
    */
  override final def descriptor: Descriptor = {
    import Service.{named, pathCall}
    // @formatter:off
    named("mailer").withCalls(
      pathCall("/api/hello/:id", hello _),
      pathCall("/api/hello/:id", useGreeting _),
      pathCall("/api/email/:id", helloEmail _)
    ).withAutoAcl(true)
    // @formatter:on
  }
}

/**
  * The greeting message class.
  */
//TODO: to be removed
case class GreetingMessage(message: String)

final case class MailContent(subject: String, to: String, body: String)


//TODO: to be removed
object GreetingMessage {
  /**
    * Format for converting greeting messages to and from JSON.
    *
    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
    */
  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
}
