package com.drew.troops

import cats.Applicative
import com.drew.troops.models.{LongUrl, ShortUrl}

trait Troops[F[_]] {
  def minify(url: LongUrl): F[ShortUrl]
}

object Troops {
  implicit def apply[F[_]](implicit ev: Troops[F]): Troops[F] = ev

  def impl[F[_]: Applicative]: Troops[F] = new Troops[F] {
    def minify(url: LongUrl): F[ShortUrl] =
      Greeting("Hello, " + n.name).pure[F]
  }
}
