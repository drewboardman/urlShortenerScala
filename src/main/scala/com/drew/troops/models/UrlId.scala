package com.drew.troops.models

case class UrlId(id: Int) extends AnyVal {
  def toShortUrl: ShortUrl = ShortUrl(s"drew.io/$id")
}
