package com.drew.shortener

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import com.drew.shortener.dao.Dao
import com.drew.shortener.models.{ LongUrl, ShortUrl }

trait Actions[F[_]] {
  def minify(longUrl: LongUrl)(implicit dao: Dao[F]): F[ShortUrl]

  def expand(shortUrl: ShortUrl)(implicit dao: Dao[F]): F[Either[String, LongUrl]]
}

object Actions {
  implicit def apply[F[_]](implicit ev: Actions[F]): Actions[F] = ev

  def impl[F[_]: Applicative: Sync]: Actions[F] = new Actions[F] {
    override def minify(longUrl: LongUrl)(implicit dao: Dao[F]): F[ShortUrl] =
      for {
        id <- dao.add(longUrl)
      } yield id.toShortUrl

    override def expand(shortUrl: ShortUrl)(implicit dao: Dao[F]): F[Either[String, LongUrl]] =
      shortUrl.toId.map { id =>
        dao.getAndUpdateHits(id)
      } match {
        case Some(io) => io.map(_.map(_.longUrl))
        case None     => Sync[F].pure(Left[String, LongUrl]("cannot parse url"))
      }
  }

}
