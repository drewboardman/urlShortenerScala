package com.drew.troops.util

import com.drew.troops.models.{Record, UrlId}

import scala.collection.mutable

object RichHashMap {

  implicit class RichHashMap(hm: mutable.HashMap[UrlId, Record]) {

    def nextId(randomizer: (List[String]) => String): UrlId =
      UrlId(randomizer(hm.keys.map(_.id).toList))
  }

}
