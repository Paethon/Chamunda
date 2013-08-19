package com.gmail.chamoners.chamunda

import scala.language.implicitConversions
import org.bukkit.Location

object Preamble {
  implicit def runnable(f: ⇒ Any): Runnable = new Runnable() { def run() = f }
  implicit def location2Point(l: Location) = Point(l.getBlockX, l.getBlockZ)
}