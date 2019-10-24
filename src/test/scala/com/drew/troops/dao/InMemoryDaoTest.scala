package com.drew.troops.dao

import cats.effect.IO
import com.drew.troops.models._
import org.scalatest.{Matchers, WordSpec}

import scala.collection.mutable

class InMemoryDaoTest extends WordSpec with Matchers {
  "add" should {
    "add new entries" in {
      val dao = new InMemoryDao[IO](
        mutable.HashMap[UrlId, Record]().empty,
        (ids) => ids.lastOption.map(_ + "1").getOrElse("helloworld")
      )
      val res: IO[UrlId] = dao.add(LongUrl("www.google.com"))
      res.unsafeRunSync() should be(UrlId("helloworld"))

      val res2: IO[UrlId] = dao.add(LongUrl("www.facebook.com"))
      res2.unsafeRunSync() should be(UrlId("helloworld1"))
    }

    "not double add the same url" in {
      val dao = new InMemoryDao[IO](
        mutable.HashMap[UrlId, Record]().empty,
        (ids) => ids.lastOption.map(_ + "1").getOrElse("foobar")
      )
      val res: IO[UrlId] = dao.add(LongUrl("www.netflix.com"))
      res.unsafeRunSync() should be(UrlId("foobar"))

      val res2: IO[UrlId] = dao.add(LongUrl("www.netflix.com"))
      res2.unsafeRunSync() should be(UrlId("foobar"))
    }
  }

  "getAndUpdateHits" should {
    "return the associated long url" in {
      val dao = new InMemoryDao[IO](
        mutable.HashMap[UrlId, Record](
          (UrlId("abc") -> Record(LongUrl("www.bing.com"), 0))
        ),
        (ids) => ids.lastOption.map(_ + "1").getOrElse("def")
      )

      val res: IO[Either[String, Record]] = dao.getAndUpdateHits(UrlId("abc"))
      res.unsafeRunSync() should be(
        Right(Record(LongUrl("www.bing.com"), 1))
      )

      val res2: IO[Either[String, Record]] = dao.getAndUpdateHits(UrlId("abc"))
      res2.unsafeRunSync() should be(
        Right(Record(LongUrl("www.bing.com"), 2))
      )
    }
  }
}
