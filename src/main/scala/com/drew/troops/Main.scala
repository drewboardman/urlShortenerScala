package com.drew.troops

import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import com.drew.troops.dao.InMemoryDao
import com.drew.troops.models.{Record, UrlId}
import com.drew.troops.util.Randomizer

import scala.collection.mutable

object Main extends IOApp {
  def run(args: List[String]): IO[ExitCode] = {
    implicit val dao: InMemoryDao[IO] = new InMemoryDao(
      mutable.HashMap[UrlId, Record]().empty,
      Randomizer.uniqueStr // <-- abstract over the randomizer to allow for testability
    )
    TroopsServer.stream[IO].compile.drain.as(ExitCode.Success)
  }
}
