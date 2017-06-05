package com.artamedia.finscrapes

import java.time.LocalDate

trait Scraper {
  def get: Seq[Tick]
}
