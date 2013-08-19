package com.gmail.chamoners.chamunda

class Village(c: Point, width: Int, height: Int) {

}

object Village {
  def apply(c: Point, width: Int, height: Int) = {
    new Village(c: Point, width: Int, height: Int)
  }

  def apply(from: Point, to: Point) = {
    val difx = from.x - to.x
    val difz = from.z - to.z

    //new Village(p: Point, width: Int, height: Int)
  }
}