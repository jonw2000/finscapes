package com.artamedia.finscrapes

import java.time.LocalDate

case class Tick(symbol: String, date: LocalDate, price: Double)
