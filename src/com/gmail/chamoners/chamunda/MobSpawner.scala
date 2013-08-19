package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import scala.util.{ Random ⇒ rnd }
import org.bukkit.entity.CreatureType
import scala.collection.mutable.HashMap
import org.bukkit.entity.LivingEntity

case class Point(x: Int, z: Int)
case class MobState(spawnPoint: Point) {
  var promoted = false
  var demoted = false
}

class MobSpawner(val center: Point,
                 val spawnsize: Int, val xCitySize: Int, val zCitySize: Int,
                 val plugin: JavaPlugin) {
  val spawnArray = Array.fill[Double](spawnsize, spawnsize)(1.0)
  val server = plugin.getServer
  val world = server.getWorlds.get(0)
  val mobStates = HashMap.empty[LivingEntity, MobState]

  // Set spawn probability inside city to zero
  for (
    x <- (spawnsize / 2) - (xCitySize / 2) until (spawnsize / 2) + (xCitySize / 2);
    z <- (spawnsize / 2) - (zCitySize / 2) until (spawnsize / 2) + (zCitySize / 2)
  ) spawnArray(x)(z) = 0.0

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

  def addCreature(mob: LivingEntity, spawnPoint: Point) {
    mobStates += (mob -> MobState(spawnPoint))
  }

  def removeCreature(mob: LivingEntity) {
    mobStates -= mob
  }

  def spawn(creatureType: CreatureType) = {
    val p = spawnPoint
    val mob = world.spawnCreature(world.getHighestBlockAt(p.x, p.z).getLocation,
      creatureType)
  }

  /**
   * Multiplies all spawn points around center with value
   * @param center Location in spawn array around which the values should be
   * multiplied
   * @param radius "Radius" around center to multiply
   * @param value value with which the entries should be multiplied
   */
  private def multiply(center: Point, radius: Int, value: Double) {
    for (
      x <- center.x - radius until center.x + radius;
      z <- center.z - radius until center.z + radius
    ) spawnArray(x)(z) *= value
  }
  /**
   *
   */
  def promote(e: LivingEntity, radius: Int = 5, amount: Double = 1.2) {
    val state = mobStates(e)
    if (!state.promoted) {
      multiply(state.spawnPoint, radius, amount)
      state.promoted = true
    }
  }

  def demote(e: LivingEntity, radius: Int = 5, amount: Double = 0.8) {
    val state = mobStates(e)
    if (!state.demoted) {
      multiply(state.spawnPoint, radius, amount)
      state.demoted = true
    }
  }
}