package com.drew.troops.models

case class UrlId(id: String) extends AnyVal {
  def toShortUrl: ShortUrl = ShortUrl(s"troops.ai/$id")
}
