package com.example.mailer.api

import akka.{Done, NotUsed}
import com.lightbend.lagom.scaladsl.api.{Descriptor, Service, ServiceCall}
import play.api.libs.json.{Format, Json}

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

//  /**
//    * Example: curl http://localhost:9000/api/hello/Alice
//    */
//  //TODO: to be removed
//  def hello(id: String): ServiceCall[NotUsed, String]
//
//  /**
//    * Example: curl -H "Content-Type: application/json" -X POST -d '{"message":
//    */
//  //TODO: to be removed
//  def useGreeting(id: String): ServiceCall[GreetingMessage, Done]

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
    * It defines the service name and the REST endpoints it offers.
    *
    * For each endpoint, declare an abstract method in the service interface.
    *
    * @return a service Descriptor
    */
  override final def descriptor: Descriptor = {
    import Service.{named, pathCall}
    named("mailer").withCalls(
//      pathCall("/api/hello/:id",      hello _),
//      pathCall("/api/hello/:id",      useGreeting _),
      pathCall("/api/email/:subject", sendHelloEmail _)
    ).withAutoAcl(true)   // Generate service ACLs from each call's path pattern
  }
}



final case class MailContent(subject: String, to: String, body: String)



///**
//  * The greeting message class.
//  */
////TODO: to be removed
//case class GreetingMessage(message: String)



////TODO: to be removed
//object GreetingMessage {
//  /**
//    * Format for converting greeting messages to and from JSON.
//    *
//    * This will be picked up by a Lagom implicit conversion from Play's JSON format to Lagom's message serializer.
//    */
//  implicit val format: Format[GreetingMessage] = Json.format[GreetingMessage]
//}
