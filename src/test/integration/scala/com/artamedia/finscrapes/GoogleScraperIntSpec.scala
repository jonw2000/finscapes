package com.artamedia.finscrapes

import java.time.LocalDate

import org.specs2.mutable.Specification

class GoogleScraperIntSpec extends Specification {

  "GoogleScraperSpec" should {
    "extract data for one day" in {
      val symbol = "LCOP.L"
      val start = LocalDate.parse("2017-01-17")
      val end = LocalDate.parse("2017-01-17")
      val gs = GoogleScraper(symbol, start, end, SimpleWebProvider)
      val res = gs.get
      res.size mustEqual 1
      res.head mustEqual Tick(symbol, LocalDate.parse("2017-01-17"), 6.59)
    }

    "extract data for one month" in {
      val symbol = "AAL.L"
      val start = LocalDate.parse("2017-01-17")
      val end = LocalDate.parse("2017-02-17")
      val gs = GoogleScraper(symbol, start, end, SimpleWebProvider)
      val res = gs.get
      res.size mustEqual 24
      res.head mustEqual Tick(symbol, LocalDate.parse("2017-02-17"), 1345)
      res.last mustEqual Tick(symbol, LocalDate.parse("2017-01-17"), 1324)
    }

    "extract data for six months" in {
      val symbol = "AGM.L"
      val start = LocalDate.parse("2016-06-01")
      val end = LocalDate.parse("2016-12-01")
      val gs = GoogleScraper(symbol, start, end, SimpleWebProvider)
      val res = gs.get
      res.size mustEqual 131
      res.head mustEqual Tick(symbol, LocalDate.parse("2016-12-01"), 141)
      res.last mustEqual Tick(symbol, LocalDate.parse("2016-06-01"), 169)
    }

    "extract data for one year" in {
      val symbol = "COB.L"
      val start = LocalDate.parse("2016-01-01")
      val end = LocalDate.parse("2017-01-01")
      val gs = GoogleScraper(symbol, start, end, SimpleWebProvider)
      val res = gs.get
      res.size mustEqual 250
      res.head mustEqual Tick(symbol, LocalDate.parse("2016-12-30"), 163.7)
      res.last mustEqual Tick(symbol, LocalDate.parse("2016-01-04"), 277.7)
    }
  }
}
