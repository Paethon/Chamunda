package com.gmail.chamoners.chamunda

import org.bukkit.entity.LivingEntity
import java.util.HashSet
import org.bukkit.Material

object RichLivingEntity {
  val transparencyHS = new HashSet[java.lang.Byte]()

  transparencyHS.add((0x00).byteValue)
}

class RichLivingEntity(val e: LivingEntity) {
  import RichLivingEntity._
  def blockInFront = {
    val block = e.getTargetBlock(transparencyHS, 1)
    if (block.getType() == Material.AIR)
      None
    else
      Some(block)
  }
}