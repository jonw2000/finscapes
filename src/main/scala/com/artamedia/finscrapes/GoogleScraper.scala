package com.artamedia.finscrapes

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import org.jsoup.Jsoup
import org.jsoup.nodes.Document

import scala.collection.JavaConverters._


case class GoogleScraper(symbol: String, start: LocalDate, end: LocalDate, provider: WebProvider) extends Scraper {

  private val base_url =
    "https://www.google.co.uk/finance/historical?" +
    "cid=$CID$" +
    "&startdate=$SMMM$%20$SD$%2C%20$SYYYY$" +
    "&enddate=$SMMM$%20$SD$%2C%20$SYYYY$" +
    "&num=200" +
    "&start=0" +
    "&ei=-KEyWYihApbDU-C2g-gM"

  private val dayFormatter = DateTimeFormatter.ofPattern("d")
  private val monthFormatter = DateTimeFormatter.ofPattern("MMM")
  private val yearFormatter = DateTimeFormatter.ofPattern("yyyy")
  private val url =
    base_url
      .replace("$CID$", getCid(symbol).getOrElse(""))
      .replace("$SD$", start.format(dayFormatter))
      .replace("$SMMM$", start.format(monthFormatter))
      .replace("$SYYYY$", start.format(yearFormatter))
      .replace("$ED$", end.format(dayFormatter))
      .replace("$EMMM$", end.format(monthFormatter))
      .replace("$EYYYY$", end.format(yearFormatter))

  private val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

  override def get: Seq[Tick] = {
    val html = provider.get(url)
    val doc: Document = Jsoup.parse(html)

    val table = doc.getElementsByClass("historical_price")
    if (table.isEmpty) return Seq()

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

  private def getCid(symbol: String): Option[String] = {
    val url = "https://www.google.co.uk/finance?q=$SYM$&ei=qhQ7WZCpNofCUdPer6gD"
    val html = provider.get(url.replace("$SYM$", symbol))
    val doc: Document = Jsoup.parse(html)
    val els = doc.getElementsByAttributeValue("rel", "canonical").asScala
    if (els.isEmpty) return None
    val res = Some(els.head.attr("href").split("=")(1))
    res
  }
}