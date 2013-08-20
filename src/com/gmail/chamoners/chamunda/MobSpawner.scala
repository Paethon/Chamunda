package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import scala.util.{ Random => rnd }
import org.bukkit.entity.CreatureType
import scala.collection.mutable.HashMap
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.EntityType
import org.bukkit.entity.Entity
import org.bukkit.entity.Creature
import Preamble._
import java.io.File
import java.awt.image.BufferedImage
import java.awt.Color
import javax.imageio.ImageIO

case class MobState(spawnPoint: Point) {
  var promoted = false
  var demoted = false
}

class MobSpawner(val center: Point,
                 val xSpawnSize: Int, val zSpawnSize: Int,
                 val xCitySize: Int, val zCitySize: Int,
                 val env: Environment) {
  val spawnArray = Array.fill[Double](xSpawnSize, zSpawnSize)(1.0)
  val mobStates = HashMap.empty[Entity, MobState]

  // Set spawn probability inside city to zero
  for (
    x <- (xSpawnSize / 2) - (xCitySize / 2) until (xSpawnSize / 2) + (xCitySize / 2);
    z <- (zSpawnSize / 2) - (zCitySize / 2) until (zSpawnSize / 2) + (zCitySize / 2)
  ) spawnArray(x)(z) = 0.0

  private def spawnPoint = {
    var probability = 0.0
    var x = 0
    var z = 0

    do {
      x = rnd.nextInt(xSpawnSize)
      z = rnd.nextInt(zSpawnSize)

      probability = spawnArray(x)(z)
    } while (rnd.nextDouble > probability)
    Point(center.x + x - xSpawnSize / 2, center.z + z - zSpawnSize / 2)
  }

  def addCreature(mob: Entity, spawnPoint: Point) {
    mobStates += (mob -> MobState(spawnPoint))
  }

  def removeCreature(mob: Entity) {
    mobStates -= mob
  }

  def spawn(entityType: EntityType) = {
    val p = spawnPoint
    val mob = env.world.spawnEntity(env.world.getHighestBlockAt(p.x, p.z).getLocation,
      entityType)
    addCreature(mob, p)
    mob.asInstanceOf[Creature].setTarget(env.world.getPlayers.get(0))
  }

  /**
   * Multiplies all spawn points around center with value
   * @param center Location in spawn array around which the values should be
   * multiplied
   * @param radius "Radius" around center to multiply
   * @param value value with which the entries should be multiplied
   */
  private def multiply(center: Point, radius: Int, value: Double) {
    val xBegin = (center.x - radius) max 0
    val xEnd = (center.x + radius) min (xSpawnSize - 1)
    val zBegin = (center.z - radius) max 0
    val zEnd = (center.z + radius) min (zSpawnSize - 1)
    for (x <- xBegin until xEnd; z <- zBegin until zEnd)
      spawnArray(x)(z) *= value
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

  def writeProbability(filename: String) {
    val image = new BufferedImage(xSpawnSize, zSpawnSize, 
        BufferedImage.TYPE_INT_ARGB)
    
    for(x <- 0 until xSpawnSize; z <- 0 until zSpawnSize) {
        val v = spawnArray(x)(z).floatValue min 1.0.floatValue
        val c = new Color(v,v,v)
    	val buf = image.setRGB(x, z, c.getRGB())
    }
    
    // Save file
    val file = new File(filename)
    ImageIO.write(image, "png", file)
  }
}