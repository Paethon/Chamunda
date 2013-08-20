package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import org.bukkit.Location
import org.bukkit.World
import scala.collection.JavaConverters._
import Preamble._
import com.gmail.chamoners.chamunda.Zeit._
import org.bukkit.entity.Villager

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
  var zeit: Zeit = calcZeit()

  def calcZeit(): Zeit = {
    val time = world.getTime()
    if (time >= Zeit.Dawn.getTime() && time < Zeit.Day.getTime()) Zeit.Dusk
    else if (time >= Zeit.Day.getTime() || time < Zeit.Dusk.getTime()) Zeit.Day
    else if (time >= Zeit.Dusk.getTime() && time < Zeit.Night.getTime()) Zeit.Dusk
    else if (time >= Zeit.Night.getTime() && time < Zeit.Dawn.getTime()) Zeit.Night
    else throw new Exception("ZeitCalc failed, shouldn't happen")
  }

  def changeZeit(newZeit: Zeit) {
    zeit = newZeit
    server.getPluginManager().callEvent(new ZeitgeberEvent(newZeit))
  }

  def execute(ticks: Int = 0)(f: => Any) = {
    server.getScheduler().scheduleSyncRepeatingTask(plugin, f, 0, ticks)
  }

  def executeOnce(delay: Int = 0)(f: => Any) = {
    server.getScheduler().scheduleSyncDelayedTask(plugin, f, delay)
  }

  def randomPlayer = {
    val players = world.getPlayers.asScala
    if (players.length > 0)
      Some(players(util.Random.nextInt(players.length)))
    else
      None
  }

  def randomVillager = {
    val livingEntityList = world.getLivingEntities.asScala
    val villagerList = livingEntityList.filter(_.isInstanceOf[Villager])
    if (villagerList.length > 0)
      Some(villagerList(util.Random.nextInt(villagerList.length)))
    else
      None
  }
}

