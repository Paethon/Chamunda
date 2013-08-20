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
  val healthMap = HashMap.empty[Block, Int]

  def attack(b: Block) {
    import Material._
    val initialHealth = b.getType match {
      case DIRT          => 5
      case STONE         => 10
      case DIAMOND_BLOCK => 20
      case _             => 5
    }
    val health = healthMap.getOrElseUpdate(b, initialHealth)

    if (health == 1) {
      b.setType(AIR)
      healthMap -= b
    } else {
      healthMap(b) = health - 1
      env.world.playSound(b.getLocation, Sound.DIG_WOOD, 2, 1)
    }
  }
}