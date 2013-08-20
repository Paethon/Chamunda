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
    WOOD_DOOR -> 100,
    WOOD -> 50)

  def attack(b: Block) {
    val blockType = b.getType
    if (initialHealth.contains(blockType)) {
      val health = healthMap.getOrElseUpdate(b, initialHealth(blockType))

      if (health == 1) {
        b.setType(AIR)
        healthMap -= b
      } else {
        healthMap(b) = health - 1
        env.world.playSound(b.getLocation, Sound.DIG_WOOD, 1, 1)
      }
    }
  }
}
