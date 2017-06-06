package com.artamedia.finscrapes

trait Scraper {
  def get: Seq[Tick]
}
