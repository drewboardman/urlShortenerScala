package com.drew.shortener

import cats.effect.{ ConcurrentEffect, Timer }
import com.drew.shortener.dao.Dao
import fs2.Stream
import org.http4s.implicits._
import org.http4s.server.blaze.BlazeServerBuilder
import org.http4s.server.middleware.Logger

object Server {

  def stream[F[_]: ConcurrentEffect](implicit T: Timer[F], dao: Dao[F]): Stream[F, Nothing] = {
    implicit val shortenerAlg: Actions[F] = Actions.impl[F]

    for {
      exitCode <- BlazeServerBuilder[F]
                   .bindHttp(8080, "0.0.0.0")
                   .withHttpApp(
                     Logger.httpApp(logHeaders = true, logBody = true)(
                       Routes.routes[F].orNotFound
                     )
                   )
                   .serve
    } yield exitCode
  }.drain
}
