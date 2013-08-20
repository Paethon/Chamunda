package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import org.bukkit.Location
import org.bukkit.World
import Preamble._

case class Point(x: Int, z: Int) {
  def -(op: Point): Point = {
    new Point(x - op.x, z - op.z)
  }

  def +(op: Point): Point = {
    new Point(x + op.x, z + op.z)
  }

  def /(op: Int): Point = {
    new Point(x / op, z / op)
  }

  def apply(wrld: World): Location = {
    new Location(wrld, x, 0, z)
  }

  def abs(): Point = {
    new Point(math.abs(x), math.abs(z))
  }

}

case class Environment(plugin: JavaPlugin) {
  val server = plugin.getServer
  val world = server.getWorlds.get(0) //Plugin affects only Default World
  val log = Logger.getLogger("Minecraft")
  
  def execute(ticks:Int = 0)(f: => Any) = {
    server.getScheduler().scheduleSyncRepeatingTask(plugin, f, 0, ticks)
  }
}

