package com.artamedia.finscrapes

trait WebProvider {
  def get(url: String): String
}

