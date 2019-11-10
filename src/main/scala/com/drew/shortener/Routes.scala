package com.drew.shortener

import cats.effect.Sync
import cats.implicits._
import com.drew.shortener.dao.Dao
import com.drew.shortener.models.{ LongUrl, ShortUrl }
import org.http4s.HttpRoutes
import org.http4s.dsl.Http4sDsl

object Routes {

  def routes[F[_]: Sync: Actions: Dao]: HttpRoutes[F] = {
    val dsl = new Http4sDsl[F] {}
    import dsl._
    HttpRoutes.of[F] {
      case GET -> Root / "minify" / longUrlStr =>
        for {
          shortUrl <- Actions[F].minify(LongUrl(longUrlStr))
          resp     <- Ok(shortUrl.url)
        } yield resp
      case GET -> Root / "expand" / shortUrlStr =>
        for {
          shortUrl <- Actions[F].expand(ShortUrl(shortUrlStr))
          resp <- shortUrl match {
                   case Right(good) => Ok(good.url)
                   case Left(err)   => BadRequest(err.toString)
                 }
        } yield resp
    }
  }
}
