package com.drew.troops.dao

import com.drew.troops.models.{ LongUrl, Record, UrlId }

trait Dao[F[_]] {
  def getAndUpdateHits(id: UrlId): F[Either[String, Record]]
  def add(longUrl: LongUrl): F[UrlId]
}