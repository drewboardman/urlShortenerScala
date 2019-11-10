package com.drew.shortener.dao

import com.drew.shortener.models.{ LongUrl, Record, UrlId }

trait Dao[F[_]] {
  def getAndUpdateHits(id: UrlId): F[Either[String, Record]]
  def add(longUrl: LongUrl): F[UrlId]
}
