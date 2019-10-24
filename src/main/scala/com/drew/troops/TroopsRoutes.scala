package com.drew.troops

import cats.effect.Sync
import cats.implicits._
import com.drew.troops.dao.Dao
import com.drew.troops.models.{LongUrl, ShortUrl}
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object TroopsRoutes {

  def routes[F[_] : Sync](implicit troopsInstance: Troops[F], dao: Dao[F]): HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "minify" / longUrlStr =>
        for {
          shortUrl <- Troops[F].minify(LongUrl(longUrlStr))
          resp <- Ok(shortUrl.url)
        } yield resp
      case GET -> Root / "expand" / shortUrlStr =>
        for {
          shortUrl <- Troops[F].expand(ShortUrl(shortUrlStr))
          resp <- shortUrl match {
            case Right(good) => Ok(good.url)
            case Left(err) => BadRequest(err.toString)
          }
        } yield resp
    }
  }
}
