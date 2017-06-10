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
    "&enddate=$EMMM$%20$ED$%2C%20$EYYYY$" +
    "&num=200" +
    "&start=$N$" +
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

    def getPages(startFrom: Int, acc: Seq[Tick]): Seq[Tick] = {
      val urlWithStartFrom = url.replace("$N$", startFrom.toString)
      val html = provider.get(urlWithStartFrom)
      val doc: Document = Jsoup.parse(html)

      val table = doc.getElementsByClass("historical_price")
      if (table.isEmpty) return acc

      val rows = table.first().getElementsByTag("tr").asScala
      val res = rows.flatMap(row => {
        val cells = row.getElementsByTag("td").asScala
        if (cells.nonEmpty) {
          val date = cells(0)
          val close = cells(4)
          Some(Tick(symbol, LocalDate.parse(date.text(), formatter), close.text().replaceAll(",","").toDouble))
        } else None
      })
      if (res.isEmpty) {
        acc
      } else {
        getPages(startFrom + 200, acc ++ res)
      }
    }

    getPages(0, Seq())
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