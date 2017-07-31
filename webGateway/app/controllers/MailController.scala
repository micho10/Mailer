package controllers

import javax.inject.Inject

import play.api.mvc.Action


/**
  * The @Inject tag indicates it's using an injected routes generator.
  */
class MailController @Inject() extends AbstractController {

  def sendEmail = ???
//    Action {
//    val cid = "1234"
//    val email = Email(
//      "Simple email",
//      "Mister FROM <test@email.com>",
//      Seq("Miss TO <carlos.sainz@globalsign.com>"),
//      // Adds attachment
//      attachments = Seq(
//        AttachmentFile("favicon.png", new File(environment.classLoader.getResource("public/images/favicon.png").getPath), contentId = Some(cid)),
//        AttachmentData("data.txt", "data".getBytes, "text/plain", Some("Simple data"), Some(EmailAttachment.INLINE))
//      ),
//      // Sends text, HTML or both ...
//      bodyText = Some("A text message"),
//      bodyHtml = Some(s"""<html><body><p>An <b>html</b> message with cid <img src="cid:$cid"></p></body></html>""")
//    )
//    val id = mailer.send(email)
//    Ok(s"Email $id sent!")
//  }

  def sendWithCustomMailer = ???
//    Action {
//    val mailer = new SMTPMailer(SMTPConfiguration("typesafe.org", 1234))
//    val id = mailer.send(Email("Simple email", "Mister FROM <from@email.com>"))
//    Ok(s"Email $id sent!")
//  }


}
