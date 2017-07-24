package com.example.mailer.api

import java.util.UUID

//import io.circe.{Decoder, Encoder}

/**
  * Adding <code>val</code> to the id parameter will add a Scala style getter for id.
  *
  * <code>AnyVal</code> works as a boxed value type. This value class can only contain a single public field,
  * and at compile time, the compiler will attempt to remove all indirect access of that field and replace it with direct access.
  * Thus, this creates a wrapper type for UUID without sacrificing performance at runtime.
  *
  * @param id unique Id
  */
class MailId(val id: UUID) extends AnyVal {
  override def toString: String = id.toString
}


object MailId {

  /* Factory methods */

  def apply(): MailId = new MailId(UUID.randomUUID())

  def apply(id: UUID): MailId = new MailId(id)


//  implicit val mailIdDecoder: Decoder[MailId] = Decoder.decodeUUIP.map(MailId(_))
//
//  implicit val mailIdEncoder: Encoder[MailId] = Encoder.encodeUUIP.contramap(_.id)

}
