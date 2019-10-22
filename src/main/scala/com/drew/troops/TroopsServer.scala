package com.drew.troops

import cats.effect.{ConcurrentEffect, Timer}
import fs2.Stream
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

object TroopsServer {

  def stream[F[_] : ConcurrentEffect](implicit T: Timer[F]): Stream[F, Nothing] = {
    implicit val troopsAlg: Troops[F] = Troops.impl[F]

    for {
      exitCode <- BlazeServerBuilder[F]
        .bindHttp(8080, "0.0.0.0")
        .withHttpApp(
          Logger.httpApp(logHeaders = true, logBody = true)(TroopsRoutes.routes[F].orNotFound)
        )
        .serve
    } yield exitCode
    }.drain
}
