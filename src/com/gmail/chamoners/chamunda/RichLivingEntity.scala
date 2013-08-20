package com.gmail.chamoners.chamunda

import org.bukkit.entity.LivingEntity
import java.util.HashSet
import org.bukkit.Material

object RichLivingEntity {
  val transparencyHS = new HashSet[java.lang.Byte]()

  transparencyHS.add((0x00).byteValue)
}

class RichLivingEntity(val e: LivingEntity)(implicit env:Environment) {
  import RichLivingEntity._
  
  def blockInFront = e.getTargetBlock(transparencyHS, 1)

  /**
   * Returns either the block below the one currently looked at or if the
   * block below is air the block that is looked at.
   */
  def blockToAttack = {
    import Material._
    blockInFront.getType match {
      case AIR => 
        val lowerLoc = blockInFront.getLocation.subtract(0, 1, 0)
        val lowerBlock = env.world.getBlockAt(lowerLoc)
        lowerBlock.getType match {
          case AIR => None
          case _ => Some(lowerBlock)
        }
      case _ => Some(blockInFront)
    }
  }
}