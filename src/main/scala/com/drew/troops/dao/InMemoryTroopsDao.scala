package com.drew.troops.dao

import cats.effect.IO
import com.drew.troops.models.{ LongUrl, Record, UrlId }
import com.drew.troops.util.RichHashMap._

import scala.collection.mutable

/**
  * This is just a simple, in-memory implementation for the purposes of the coding challenge.
  * In a real application, you'd want to use a real db.
  *
  * @param db: passed in for ease of testing
  */
class InMemoryTroopsDao(db: mutable.HashMap[UrlId, Record]) extends TroopsDao[IO] {

  override def add(longUrl: LongUrl): IO[UrlId] = getId(longUrl).map {
    case Some(urlId) => urlId
    case None =>
      val newId = db.nextId
      db.update(newId, Record(longUrl, 0))
      newId
  }

  private def getId(longUrl: LongUrl): IO[Option[UrlId]] =
    IO(db.find(_._2.longUrl == longUrl).map(_._1))

  override def getAndUpdateHits(id: UrlId): IO[Either[String, Record]] =
    get(id).map {
      case Some(record) =>
        val newRecord = record.copy(hits = record.hits + 1)
        db.update(id, newRecord)
        Right[String, Record](newRecord)
      case None =>
        Left("url not found in db")
    }

  private def get(id: UrlId): IO[Option[Record]] = IO(db.get(id))
}
