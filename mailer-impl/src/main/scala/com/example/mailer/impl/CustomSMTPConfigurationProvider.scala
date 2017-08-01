package com.example.mailer.impl

import javax.inject.Provider

import play.api
import play.api.Environment
import play.api.inject.{Binding, Module}
import play.api.libs.mailer.SMTPConfiguration

/**
  *
  */
class CustomSMTPConfigurationProvider extends Provider[SMTPConfiguration] {
  override def get(): SMTPConfiguration = new SMTPConfiguration("example.com", 1234)
}


/**
  *
  */
class CustomMailerConfigurationModule extends Module {
  /**
    *
    * @param environment
    * @param configuration
    * @return
    */
  override def bindings(environment: Environment, configuration: api.Configuration): Seq[Binding[_]] = Seq(
    bind[SMTPConfiguration].toProvider[CustomSMTPConfigurationProvider]
  )
}
