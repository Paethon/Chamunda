package com.gmail.chamoners.chamunda

class Village(val c: Point, val width: Int, val height: Int) {
}

object Village {
  def apply(c: Point, width: Int, height: Int) = {
    new Village(c: Point, width: Int, height: Int)
  }

  def apply(from: Point, to: Point) = {
    val diff = (from - to).abs
    val center = (from + to) / 2

    new Village(center, diff.x, diff.z)
  }
}