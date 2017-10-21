package com.artamedia.finscrapes

import java.time.LocalDate

import org.jsoup.Jsoup
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver

class IGScraper extends Scraper {

  private val knownSymbols = Set("FTSE 100")

  override def get(symbol: String)(implicit provider: WebProvider): Option[Tick] = {
    if (knownSymbols.contains(symbol)) {
      None
    } else None
  }

  def get2(symbol: String)(implicit provider: WebProvider): Option[Tick] = {
    if (knownSymbols.contains(symbol)) {
//      import org.openqa.selenium.chrome
      implicit val impdriver: WebDriver = new ChromeDriver

      None
    } else None
  }
}
