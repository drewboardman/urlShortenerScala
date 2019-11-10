package com.drew.shortener.models

case class UrlId(id: String) extends AnyVal {
  def toShortUrl: ShortUrl = ShortUrl(s"drew.io/$id")
}
