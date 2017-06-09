package com.artamedia.finscrapes

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.JavaConverters._


case class GoogleScraper(symbol: String, start: LocalDate, end: LocalDate, provider: WebProvider) extends Scraper {

  private val url = "https://www.google.co.uk/finance/historical?cid=665938&startdate=Jun%201%2C%202001&enddate=Jun%203%2C%202017&num=200&start=400&ei=-KEyWYihApbDU-C2g-gM"

  private val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

  override def get: Seq[Tick] = {
    val doc: Document = Jsoup.parse(provider.get(url))
    val table = doc.getElementsByClass("historical_price")
    val rows = table.first().getElementsByTag("tr").asScala
    val res = rows.flatMap(row => {
      val cells = row.getElementsByTag("td").asScala
      if (cells.nonEmpty) {
        val date = cells(0)
        val close = cells(4)
        Some(Tick(symbol, LocalDate.parse(date.text(), formatter), close.text().toDouble))
      } else None
    })
    res
  }
}