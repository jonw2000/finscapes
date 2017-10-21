package com.artamedia.finscrapes

import java.time.LocalDate

import org.specs2.mutable.Specification

class YahooScraperIntSpec extends Specification {

  private implicit val provider = SimpleWebProvider

  "YahooScraper" should {
    "extract data for one day" in {
      val symbol = "LCOP.L"
      val start = LocalDate.parse("2017-01-17")
      val end = LocalDate.parse("2017-01-17")
      val gs = new YahooScraper
      val res = gs.get(symbol)
      res.size mustEqual 1
      res.head mustEqual Tick(symbol, LocalDate.parse("2017-01-17"), 6.59)
    }
  }
}
