package com.gmail.chamoners.chamunda

import scala.language.implicitConversions
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable

object Preamble {
  implicit def function2Runnable(f: => Any): Runnable = new Runnable() { def run() = f }
  /*implicit def function2BukkitRunnable(f: => Any):BukkitRunnable = {
    new BukkitRunnable {
      def run() {
        f
      }
    }
  }*/
  implicit def location2Point(l: Location) = Point(l.getBlockX, l.getBlockZ)
}