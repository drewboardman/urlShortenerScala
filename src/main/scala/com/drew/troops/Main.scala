package com.drew.troops

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] =
    TroopsServer.stream[IO].compile.drain.as(ExitCode.Success)
}
