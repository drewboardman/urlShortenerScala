package com.drew.troops

import cats.Applicative
import cats.effect.Sync
import com.drew.troops.models.{ LongUrl, ShortUrl }

trait Troops[F[_]] {
  def minify(longUrl: LongUrl): F[ShortUrl]
}

object Troops {
  implicit def apply[F[_]](implicit ev: Troops[F]): Troops[F] = ev

  def impl[F[_]: Applicative: Sync]: Troops[F] = new Troops[F] {
    def minify(longUrl: LongUrl): F[ShortUrl] = Sync[F].delay(ShortUrl("hello"))
  }
}
