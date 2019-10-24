package com.drew.troops.dao

import cats.effect.IO
import com.drew.troops.models._
import org.scalatest.{ Matchers, WordSpec }

import scala.collection.mutable

class InMemoryDaoTest extends WordSpec with Matchers {
  "add" should {
    "add new entries" in {
      val dao            = new InMemoryDao(mutable.HashMap[UrlId, Record]().empty)
      val res: IO[UrlId] = dao.add(LongUrl("www.google.com"))
      res.unsafeRunSync() should be(UrlId(1))

      val res2: IO[UrlId] = dao.add(LongUrl("www.facebook.com"))
      res2.unsafeRunSync() should be(UrlId(2))
    }

    "not double add the same url" in {
      val dao            = new InMemoryDao(mutable.HashMap[UrlId, Record]().empty)
      val res: IO[UrlId] = dao.add(LongUrl("www.netflix.com"))
      res.unsafeRunSync() should be(UrlId(1))

      val res2: IO[UrlId] = dao.add(LongUrl("www.netflix.com"))
      res2.unsafeRunSync() should be(UrlId(1))
    }
  }

  "getAndUpdateHits" should {
    "return the associated long url" in {
      val dao = new InMemoryDao(
        mutable.HashMap[UrlId, Record](
          (UrlId(1) -> Record(LongUrl("www.bing.com"), 0))
        )
      )

      val res: IO[Either[String, Record]] = dao.getAndUpdateHits(UrlId(1))
      res.unsafeRunSync() should be(
        Right(Record(LongUrl("www.bing.com"), 1))
      )

      val res2: IO[Either[String, Record]] = dao.getAndUpdateHits(UrlId(1))
      res2.unsafeRunSync() should be(
        Right(Record(LongUrl("www.bing.com"), 2))
      )
    }
  }
}
