package com.drew.troops.models

case class ShortUrl(url: String) extends AnyVal {
  def toId: Option[String] = url.split("/").lastOption
}
