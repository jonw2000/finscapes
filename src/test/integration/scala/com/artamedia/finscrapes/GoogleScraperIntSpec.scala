package com.artamedia.finscrapes

import org.specs2.mutable.Specification

class GoogleScraperIntSpec extends Specification {

  private implicit val provider = SimpleWebProvider

  "GoogleScraperSpec" should {
    "extract data for one symbol" in {
      val symbol = "LCOP.L"
      val gs = new GoogleScraper
      val res: Option[Tick] = gs.get(symbol)
      res.size mustEqual 1
    }
  }
}
