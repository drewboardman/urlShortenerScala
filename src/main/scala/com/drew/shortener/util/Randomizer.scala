package com.drew.shortener.util

object Randomizer {

  @scala.annotation.tailrec
  def uniqueStr(ids: List[String]): String = {
    val rand = scala.util.Random.alphanumeric.take(6).mkString("")
    if (ids.contains(rand)) {
      uniqueStr(ids)
    } else {
      rand
    }
  }

}
