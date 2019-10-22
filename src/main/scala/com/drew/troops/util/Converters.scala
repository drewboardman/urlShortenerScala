package com.drew.troops.util

import com.drew.troops.models.{LongUrl, ShortUrl}

object Converters {
  def longToShort(long: LongUrl): ShortUrl
}
