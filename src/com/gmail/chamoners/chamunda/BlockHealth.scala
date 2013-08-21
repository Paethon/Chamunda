package com.gmail.chamoners.chamunda

import Preamble._
import scala.collection.mutable.HashMap
import org.bukkit.block.Block
import org.bukkit.Material
import org.bukkit.Effect
import org.bukkit.Sound
/**
 * Implements attacking of blocks and removes block once the health reaches 0
 */
class BlockHealth(env: Environment) {
  import Material._
  val healthMap = HashMap.empty[Block, Int]
  val initialHealth = Map(
    DIRT -> 30,
    COBBLESTONE -> 100,
    SAND -> 30,
    SANDSTONE -> 100,
    WOOD_DOOR -> 100,
    WOOD -> 50)

  import Sound._
  val sound = Map(
    DIRT -> DIG_GRAVEL,
    COBBLESTONE -> DIG_STONE,
    SAND -> DIG_SAND,
    SANDSTONE -> DIG_STONE,
    WOOD_DOOR -> DIG_WOOD,
    WOOD -> DIG_WOOD)

  def attack(b: Block) {
    val blockType = b.getType
    if(blockType == WOOD_DOOR)env.log.info("WOOD_DOOR attacked")
    if (initialHealth.contains(blockType)) {
      val health = healthMap.getOrElseUpdate(b, initialHealth(blockType))

      if (health == 1) {
        b.setType(AIR)
        healthMap -= b
      } else {
        healthMap(b) = health - 1
        env.world.playSound(b.getLocation, sound(blockType), 1, 1)
      }
    }
  }
}
