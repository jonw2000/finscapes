package com.artamedia.finscrapes

import java.time.LocalDate

import org.jsoup.Jsoup

class GoogleScraper
  extends Scraper {

  override def get(symbol: String)(implicit provider: WebProvider) = {
    def url = s"https://www.google.co.uk/search?hl=en&q=$symbol+share+price"
    val doc = Jsoup.parse(provider.get(url))
    val tag = doc.getElementsByClass("_Rnb fmob_pr fac-l")
    if (!tag.isEmpty) {
      val px = tag.get(0).text()
      Some(Tick(symbol, LocalDate.now(), px.toDouble))
    }
    else
      None
  }
}