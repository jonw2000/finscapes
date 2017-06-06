package com.artamedia.finscrapes

import java.time.LocalDate

import org.mockito.ArgumentMatchers._
import org.mockito.Mockito._
import org.scalatest.mockito.MockitoSugar
import org.specs2.mutable.Specification

class GoogleScraperSpec extends Specification with MockitoSugar {

  "GoogleScraperSpec"  {

    "extract data from som html" >> {
      val start = LocalDate.parse("2016-01-01")
      val end = LocalDate.parse("2017-01-01")
      val provider = mock[WebProvider]
      val html = "<html><head><title>First parse</title></head><body><p>Parsed HTML into a doc.</p></body></html>"

      when(provider.get(anyString())).thenReturn("")
      val gs = GoogleScraper("AGM.L", start, end, provider)
      gs.get mustEqual Seq()
    }
  }
}
