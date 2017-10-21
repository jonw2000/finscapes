package com.artamedia.finscrapes

import java.time.LocalDate

import org.jsoup.Jsoup

class YahooScraper
  extends Scraper {

  override def get(symbol: String)(implicit provider: WebProvider): Option[Tick] = {
    def url = s"https://uk.finance.yahoo.com/quote/$symbol?ltr=1"

    val doc = Jsoup.parse(provider.get(url))
    val tag = doc.getElementsByClass("Trsdu(0.3s) Fw(b) Fz(36px) Mb(-4px) D(ib)")
    if (!tag.isEmpty) {
      val px = tag.get(0).text()
      Some(Tick(symbol, LocalDate.now(), px.toDouble))
    }
    else
      None
  }

}