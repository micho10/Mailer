package com.example.mailer.impl

import javax.inject.Provider

import play.api
import play.api.Environment
import play.api.inject.{Binding, Module}
import play.api.libs.mailer.SMTPConfiguration

/**
  * [[SMTPConfiguration]]. Typically implemented by an injector. For any type [[SMTPConfiguration]] that can be injected,
  * you can also inject [[Provider<SMTPConfiguration>]]. Compared to injecting [[SMTPConfiguration]] directly, injecting
  * [[Provider<SMTPConfiguration>]] enables:
  *
  * <ul>
  *   <li>retrieving multiple instances.</li>
  *   <li>lazy or optional retrieval of an instance.</li>
  *   <li>breaking circular dependencies.</li>
  *   <li>abstracting scope so you can look up an instance in a smaller scope from an instance in a containing scope.</li>
  * </ul>
  */
@Deprecated
class CustomSMTPConfigurationProvider extends Provider[SMTPConfiguration] {
  // Provides a fully-constructed and injected instance of SMTPConfiguration
  override def get(): SMTPConfiguration = new SMTPConfiguration("example.com", 1234)
}



/**
  * A Play dependency injection module.
  *
  * Dependency injection modules can be used by Play plugins to provide bindings for JSR-330 compliant ApplicationLoaders.
  * Any plugin that wants to provide components that a Play application can use may implement one of these.
  */
@Deprecated
class CustomMailerConfigurationModule extends Module {

  /**
    *
    * @param environment   The environment for the application. Captures concerns relating to the classloader and the filesystem
    *                      for the application.
    * @param configuration A full configuration set.
    * @return              A sequence of bindings. Bindings are used to bind classes, optionally qualified by a JSR-330 qualifier
    *                      annotation, to instances, providers or implementation classes.
    */
  override def bindings(environment: Environment, configuration: api.Configuration): Seq[Binding[_]] = Seq(
    bind[SMTPConfiguration].toProvider[CustomSMTPConfigurationProvider]
  )
}
