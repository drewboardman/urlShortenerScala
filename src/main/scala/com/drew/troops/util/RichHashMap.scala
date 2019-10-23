package com.drew.troops.util

import com.drew.troops.models.{ Record, UrlId }

import scala.collection.mutable

object RichHashMap {

  implicit class RichHashMap(hm: mutable.HashMap[UrlId, Record]) {
    def nextId: UrlId = {
      val maybeMax = hm.keys.map(_.id)
      if (maybeMax.nonEmpty) UrlId(maybeMax.max + 1) else UrlId(1)
    }
  }

}
