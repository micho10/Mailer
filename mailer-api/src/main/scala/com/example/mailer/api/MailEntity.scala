package com.example.mailer.api

object MailEntity {

  /**
    * Sealed - all subclasses must be in the same file
    */
  sealed trait MailCommand
  final case class GetMail(id: MailId) extends MailCommand
  final case class CreateMail(content: MailContent) extends MailCommand
  final case class UpdateMail(id: MailId, content: MailContent) extends MailCommand

  sealed trait MailEvent {
    // Abstract because they've not been given a value
    val id: MailId
    val content: MailContent
  }
  final case class MailCreated(id: MailId, content: MailContent) extends MailEvent
  final case class MailUpdated(id: MailId, content: MailContent) extends MailEvent
  final case class MailNotFound(id: MailId) extends RuntimeException(s"Mail not found with id: $id")

  /* Either[Exception, Success] */
  type MaybeMail[+A] = Either[MailNotFound, A]

  final case class MailState(mails: Map[MailId, MailContent]) {
    def apply(id: MailId): MaybeMail[MailContent] = mails.get(id).toRight(MailNotFound(id))

    def +(event: MailEvent): MailState = MailState(mails.updated(event.id, event.content))
  }

  object MailState {
    def apply(): MailState = MailState(Map.empty)
  }

}
