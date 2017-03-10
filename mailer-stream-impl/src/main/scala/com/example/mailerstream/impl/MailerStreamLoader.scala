package com.example.mailerstream.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.mailerstream.api.MailerStreamService
import com.example.mailer.api.MailerService
import com.softwaremill.macwire._

class MailerStreamLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new MailerStreamApplication(context) {
      override def serviceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new MailerStreamApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[MailerStreamService]
  )
}

abstract class MailerStreamApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[MailerStreamService].to(wire[MailerStreamServiceImpl])
  )

  // Bind the MailerService client
  lazy val mailerService = serviceClient.implement[MailerService]
}
