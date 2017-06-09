package com.artamedia.finscrapes

import scala.io.Source

trait WebProvider {
  def get(url: String): String
}

object SimpleWebProvider extends WebProvider {
  override def get(url: String): String = Source.fromURL(url).mkString
}