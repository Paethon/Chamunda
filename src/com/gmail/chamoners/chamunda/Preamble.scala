package com.gmail.chamoners.chamunda

import scala.language.implicitConversions
import org.bukkit.Location
import org.bukkit.scheduler.BukkitRunnable
import org.bukkit.entity.LivingEntity

object Preamble {
  implicit def function2Runnable(f: => Any): Runnable = new Runnable() { def run() = f }
  implicit def location2Point(l: Location) = Point(l.getBlockX, l.getBlockZ)
  implicit def livingEntity2RichLivingEntity(e: LivingEntity)(implicit env:Environment) = new RichLivingEntity(e)(env)
}