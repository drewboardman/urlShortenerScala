package com.drew.troops.dao

import cats.effect.IO
import cats.implicits._
import com.drew.troops.models.{LongUrl, UrlId}

import scala.collection.mutable

object InMemoryTroopsDao extends TroopsDao[IO] {
  val db: mutable.HashMap[UrlId, LongUrl] =
    mutable.HashMap[UrlId, LongUrl]().empty

  override def getOrAdd(longUrl: LongUrl): IO[UrlId] = getId(longUrl).map {
    case Some(urlId) => urlId
    case None =>
      val newId = UrlId(db.keys.map(_.id).max + 1)
      db + (newId -> longUrl)
      newId
  }

  private def getId(longUrl: LongUrl): IO[Option[UrlId]] =
    IO(db.find(_._2 == longUrl).map(_._1))

  override def updateHits(id: UrlId): IO[Either[String, Unit]] = get(id).map {
    case Some(longUrl) =>
      db.update(id, longUrl.copy(hits = longUrl.hits + 1))
      Right[String, Unit](())
    case None =>
      Left("url not found in db")
  }

  private def get(id: UrlId): IO[Option[LongUrl]] = IO(db.get(id))
}
