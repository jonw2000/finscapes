package com.artamedia.finscrapes

import java.time.LocalDate

import org.specs2.mutable.Specification

class IGScraperIntSpec extends Specification {

  private implicit val provider = SimpleWebProvider

  "IGScraper" should {
    "extract data for one day" in {
      val symbol = "FTSE 100"
      val ig = new IGScraper
      val res = ig.get(symbol)
      res should ===(Some(Tick(_, _, _)))
    }
  }
}
