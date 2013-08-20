package com.gmail.chamoners.chamunda

import com.gmail.chamoners.chamunda.MobSpawner

class Village(val c: Point, val width: Int, val height: Int, env: Environment) {
  lazy val mobspawn = new MobSpawner(c, width + 20, height + 20, width, height, env)
}

object Village {
  def apply(c: Point, width: Int, height: Int, env: Environment) = {
    new Village(c: Point, width: Int, height: Int, env: Environment)
  }

  def apply(from: Point, to: Point, env: Environment) = {
    val diff = (from - to).abs
    val center = (from + to) / 2

    new Village(center, diff.x, diff.z, env: Environment)
  }
}