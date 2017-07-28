package controllers

import com.example.mailer.api.MailerService

import play.api.mvc.Controller

abstract class AbstractController(mailerApi: MailerService) extends Controller {

}
