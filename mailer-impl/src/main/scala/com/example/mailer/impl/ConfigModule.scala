package com.example.mailer.impl

import com.typesafe.config.Config
import play.api.inject.Module
import play.api.{Configuration, Environment}

/**
  * A Play dependency injection module. Provides a shim for Play 2.5.x
  *
  * Dependency injection modules can be used by Play plugins to provide bindings for JSR-330 compliant ApplicationLoaders.
  * Any plugin that wants to provide components that a Play application can use may implement one of these.
  */
class ConfigModule extends Module {

  /**
    * @param environment   The environment for the application. Captures concerns relating to the classloader and the filesystem
    *                      for the application.
    * @param configuration A full configuration set.
    * @return              A sequence of bindings. Bindings are used to bind classes, optionally qualified by a JSR-330 qualifier
    *                      annotation, to instances, providers or implementation classes.
    */
  override def bindings(environment: Environment, configuration: Configuration) = Seq(
    bind[Config].toInstance(configuration.underlying)
  )
}
