package com.example.mailer.impl

import com.lightbend.lagom.scaladsl.api.ServiceLocator
import com.lightbend.lagom.scaladsl.api.ServiceLocator.NoServiceLocator
import com.lightbend.lagom.scaladsl.persistence.cassandra.CassandraPersistenceComponents
import com.lightbend.lagom.scaladsl.server._
import com.lightbend.lagom.scaladsl.devmode.LagomDevModeComponents
import play.api.libs.ws.ahc.AhcWSComponents
import com.example.mailer.api.MailerService
import com.softwaremill.macwire._

class MailerLoader extends LagomApplicationLoader {

  override def load(context: LagomApplicationContext): LagomApplication =
    new MailerApplication(context) {
      override def serviceLocator: ServiceLocator = NoServiceLocator
    }

  override def loadDevMode(context: LagomApplicationContext): LagomApplication =
    new MailerApplication(context) with LagomDevModeComponents

  override def describeServices = List(
    readDescriptor[MailerService]
  )
}

abstract class MailerApplication(context: LagomApplicationContext)
  extends LagomApplication(context)
    with CassandraPersistenceComponents
    with AhcWSComponents {

  // Bind the services that this server provides
  override lazy val lagomServer = LagomServer.forServices(
    bindService[MailerService].to(wire[MailerServiceImpl])
  )

  // Register the JSON serializer registry
  override lazy val jsonSerializerRegistry = MailerSerializerRegistry

  // Register the Mailer persistent entity
  persistentEntityRegistry.register(wire[MailerEntity])
}
