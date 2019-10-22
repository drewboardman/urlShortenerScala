val Http4sVersion = "0.20.8"
val CirceVersion = "0.11.1"
val Specs2Version = "4.1.0"
val LogbackVersion = "1.2.3"

lazy val root = (project in file("."))
  .settings(
    organization := "com.drew",
    name := "troops",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.8",
    libraryDependencies ++= Seq(
      "org.http4s" %% "http4s-blaze-server" % Http4sVersion,
      "org.http4s" %% "http4s-blaze-client" % Http4sVersion,
      "org.http4s" %% "http4s-circe" % Http4sVersion,
      "org.http4s" %% "http4s-dsl" % Http4sVersion,
      "io.circe" %% "circe-generic" % CirceVersion,
      "org.specs2" %% "specs2-core" % Specs2Version % "test",
      "ch.qos.logback" % "logback-classic" % LogbackVersion,

      // Start with this one
      "org.tpolecat" %% "doobie-core" % "0.8.4",

      // And add any of these as needed
      "org.tpolecat" %% "doobie-h2" % "0.8.4", // H2 driver 1.4.199 + type mappings.
      "org.tpolecat" %% "doobie-hikari" % "0.8.4", // HikariCP transactor.
      "org.tpolecat" %% "doobie-postgres" % "0.8.4", // Postgres driver 42.2.8 + type mappings.
      "org.tpolecat" %% "doobie-quill" % "0.8.4", // Support for Quill 3.4.9
      "org.tpolecat" %% "doobie-specs2" % "0.8.4" % "test", // Specs2 support for typechecking statements.
      "org.tpolecat" %% "doobie-scalatest" % "0.8.4" % "test" // ScalaTest support for typechecking statements.

    ),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % "0.10.3"),
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.0")
  )

scalacOptions ++= Seq(
  "-deprecation",
  "-encoding",
  "UTF-8",
  "-language:higherKinds",
  "-language:postfixOps",
  "-feature",
  "-Ypartial-unification",
  "-Xfatal-warnings"
)
