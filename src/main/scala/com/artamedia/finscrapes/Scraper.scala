package com.artamedia.finscrapes

import java.time.LocalDate

trait Scraper {
  def get(symbol: String)(implicit provider: WebProvider): Option[Tick]
}

trait HistoricalScraper extends Scraper {
  def get(symbol: String, start: LocalDate, end: LocalDate)(implicit provider: WebProvider): Seq[Tick]
}
