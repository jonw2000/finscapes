package com.artamedia.finscrapes

import java.time.LocalDate
import org.jsoup.Jsoup
import org.jsoup.nodes.Document


case class GoogleScraper(symbol: String, start: LocalDate, end: LocalDate, provider: WebProvider) extends Scraper {

  private val url = "https://www.google.co.uk/finance/historical?cid=665938&startdate=Jun%201%2C%202001&enddate=Jun%203%2C%202017&num=200&start=400&ei=-KEyWYihApbDU-C2g-gM"

  override def get: Seq[Tick] = {
    val doc: Document = Jsoup.parse(provider.get(url))
    Seq()
  }
}