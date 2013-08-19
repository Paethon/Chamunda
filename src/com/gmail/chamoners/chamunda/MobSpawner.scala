package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import scala.util.{ Random ⇒ rnd }
import org.bukkit.entity.CreatureType
import scala.collection.mutable.HashMap
import org.bukkit.entity.LivingEntity


case class Point(x: Int, z: Int)
case class MobState(spawnPoint:Point)

class MobSpawner(val center: Point,
                 val spawnsize: Int, val xCitySize: Int, val zCitySize: Int,
                 val plugin: JavaPlugin) {
  val spawnArray = Array.fill[Double](spawnsize, spawnsize)(1.0)
  val server = plugin.getServer
  val world = server.getWorlds.get(0)
  val spawnPosition = HashMap.empty[LivingEntity, Point]

  // Set spawn probability inside city to zero
  for(x <- (spawnsize/2)-(xCitySize/2) to (spawnsize/2)+(xCitySize/2);
      z <- (spawnsize/2)-(zCitySize/2) to (spawnsize/2)+(zCitySize/2))
    spawnArray(x)(z) = 0.0
  
  private def spawnPoint = {
    var probability = 0.0
    var x = 0
    var z = 0

    do {
      x = rnd.nextInt(spawnsize)
      z = rnd.nextInt(spawnsize)

      probability = spawnArray(x)(z)
    } while (rnd.nextDouble > probability)
    Point(center.x + x - spawnsize / 2, center.z + z - spawnsize / 2)
  }

  def spawn(creatureType: CreatureType) = {
    val p = spawnPoint
    world.spawnCreature(world.getHighestBlockAt(p.x, p.z).getLocation,
      creatureType)
  }
  
  def promot(e:LivingEntity) {
    ???
  }
  
  def demote(e:LivingEntity) {
    ???
  }
}