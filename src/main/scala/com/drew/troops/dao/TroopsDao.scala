package com.drew.troops.dao

import com.drew.troops.models.{LongUrl, ShortUrl, UrlId}

trait TroopsDao[F[_]] {
  def updateHits(id: UrlId): F[Either[String, Unit]]
  def getOrAdd(longUrl: LongUrl): F[UrlId]
}
