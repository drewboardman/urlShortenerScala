package com.drew.troops

import cats.effect.Sync
import cats.{Applicative, Monad}
import com.drew.troops.dao.TroopsDao
import com.drew.troops.models.{LongUrl, ShortUrl}

trait Troops[F[_]] {
  def minify(longUrl: LongUrl)(implicit dao: TroopsDao[F]): F[ShortUrl]
}

object Troops {
  implicit def apply[F[_]](implicit ev: Troops[F]): Troops[F] = ev

  def impl[F[_]: Applicative : Sync]: Troops[F] = new Troops[F] {
    def minify(longUrl: LongUrl)(implicit dao: TroopsDao[F]): F[ShortUrl] = for {
      id <- dao.add(longUrl)
    } yield ()
}
