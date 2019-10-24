package com.drew.troops

import cats.Applicative
import cats.effect.Sync
import cats.implicits._
import com.drew.troops.dao.Dao
import com.drew.troops.models.{LongUrl, ShortUrl}

trait Troops[F[_]] {
  def minify(longUrl: LongUrl)(implicit dao: Dao[F]): F[ShortUrl]

  def expand(shortUrl: ShortUrl)(implicit dao: Dao[F]): F[Either[String, LongUrl]]
}

object Troops {
  implicit def apply[F[_]](implicit ev: Troops[F]): Troops[F] = ev

  def impl[F[_] : Applicative : Sync]: Troops[F] = new Troops[F] {
    override def minify(longUrl: LongUrl)(implicit dao: Dao[F]): F[ShortUrl] =
      for {
        id <- dao.add(longUrl)
      } yield id.toShortUrl

    override def expand(shortUrl: ShortUrl)(implicit dao: Dao[F]): F[Either[String, LongUrl]] =
      shortUrl.toId.map { id =>
        dao.getAndUpdateHits(id)
      } match {
        case Some(io) => io.map(_.map(_.longUrl))
        case None => Sync[F].pure(Left[String, LongUrl]("cannot parse url"))
      }
  }

}
