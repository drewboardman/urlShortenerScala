package com.drew.troops.models

case class ShortUrl(url: String) extends AnyVal {
  def toId: Option[UrlId] = url.split("/").lastOption.map(UrlId)
}
