import CompilerFlags._

// Resolvers
resolvers ++= Seq(
  Resolver.sonatypeRepo("snapshots"),
  Resolver.bintrayRepo("websudos", "oss-releases"),
  "Sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
  Resolver.bintrayRepo("hseeberger", "maven")
)

lazy val root = (project in file("."))
  .settings(name := "mailer")
  .aggregate(mailerApi, mailerImpl)
  .settings(commonSettings: _*)

organization in ThisBuild := "com.example"
version      in ThisBuild := "1.0-SNAPSHOT"

//mainClass    in ThisBuild := "com.example.mailer.Main"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.11.8"

val circeVersion = "0.8.0"

//val playJsonDerivedCodecs = "org.julienrf"              %  "play-json-derived-codecs_2.11" % "4.0.0"
val macwire   = "com.softwaremill.macwire"  %% "macros"        % "2.3.0"      % "provided"
val scalaTest = "org.scalatest"             %% "scalatest"     % "3.0.3"      % Test
val circe     = "io.circe" %% "circe-generic" % circeVersion
val akkaJson  =  "de.heikoseeberger" %% "akka-http-circe" % "1.16.1"

//val circeCore = "io.circe" %% "circe-core" % circeVersion
//val circeGeneric = "io.circe" %% "circe-generic" % circeVersion
//val circeParser = "io.circe" %% "circe-parser" % circeVersion

//val circe     = Seq(
//  "io.circe" %% "circe-core",
//  "io.circe" %% "circe-generic",
//  "io.circe" %% "circe-parser"
//).map(_ % circeVersion).flatten


scalacOptions ++= compilerFlags
// Uses existing values to initialize this setting
scalacOptions in (Compile, console) ~= filterExcludedReplOptions

lazy val mailerApi = (project in file("mailer-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      circe,
      akkaJson
//      circeCore,
//      circeGeneric,
//      circeParser
    )
  )

lazy val mailerImpl = (project in file("mailer-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceCassandra,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(mailerApi)

lazy val mailerStreamApi = (project in file("mailer-stream-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

lazy val mailerStreamImpl = (project in file("mailer-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(mailerStreamApi, mailerApi)


/******************************* SERVICE LOCATOR OPTIONS *******************************/
// Assign a port number to a service manually
//lazy val usersImpl = project
//  .enablePlugins(LagomScala)
//  .settings(lagomServicePort := 11000)

// Define the range of service ports available (default [49152, 65535])
//lagomServicesPortRange in ThisBuild := PortRange(40000, 45000)

// Change Service Locator port (default: 8000)
//lagomServiceLocatorPort in ThisBuild := 10000

// Comunicating with external services
//lagomUnmanagedServices in ThisBuild := Map("weather" -> "http://localhost:3333")

// Disable the Service Locator
//lagomServiceLocatorEnabled in ThisBuild := false



/******************************* CASSANDRA SERVER OPTIONS *******************************/
// Change Casssandra port (Cassandra's default = 9042 | Lagom's embedded Cassandra = 4000)
//lagomCassandraPort in ThisBuild := 9042

// Prevent all DB files from being deleted on start (cleaned up by default)
//lagomCassandraCleanOnStart in ThisBuild := false

// Provide a custom Keyspace (better done in an "application.conf" file)
//lazy val usersImpl = project
//  .settings(name := "users-impl")       // Generated Cassandra keyspace: users_impl
//
//lazy val usersImplKeyspaced = (project in file("users-impl"))
//  .enablePlugins(LagomScala)
//  .settings(
//    name := "users-impl",
//    lagomCassandraKeyspace := "users"   // Set the Cassandra keyspace name to: users
//  )

// Change the Cassandra start time (default = 20 sec)
//import scala.concurrent.duration._  // Mind that the import is needed
//lagomCassandraMaxBootWaitingTime in ThisBuild := 0.seconds

// Disable the embedded Cassandra server
//lagomCassandraEnabled in ThisBuild := false

// Connect to a locally running Cassandra instance
//lagomCassandraEnabled in ThisBuild := false
//lagomUnmanagedServices in ThisBuild := Map("cas_native" -> "http://localhost:9042")



/******************************* KAFKA SERVER OPTIONS *******************************/
// Kafka uses Zookeeper (server started on port 2181)
// Change Kafka port (Kafka's default = 9092)
//lagomKafkaPort in ThisBuild := 10000
//lagomKafkaZookeperPort in ThisBuild := 9999

// Provide a custom 'server.properties' file to configure Kafka
//lagomKafkaPropertiesFile in ThisBuild :=
//  Some((baseDirectory in ThisBuild).value / "project" / "kafka-server.properties")

// Disable the embedded Kafka server
//lagomKafkaEnabled in ThisBuild := false

// Connect to a locally running Kafka instance
//lagomKafkaEnabled in ThisBuild := false
//lagomKafkaAddress in ThisBuild := "localhost:10000"


def commonSettings: Seq[Setting[_]] = Seq(
)
