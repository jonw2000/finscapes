package com.artamedia.finscrapes

import java.time.LocalDate

import org.specs2.mutable.Specification

class GoogleScraperIntSpec extends Specification {

  "GoogleScraperSpec" should {
    "extract data for one day" in {
      val start = LocalDate.parse("2017-01-17")
      val end = LocalDate.parse("2017-01-17")
      val gs = GoogleScraper("LCOP.L", start, end, SimpleWebProvider)
      val res = gs.get
      res.size mustEqual 1
      res.head mustEqual Tick("LCOP.L", LocalDate.parse("2017-06-09"), 6.3)
    }
  }
}
