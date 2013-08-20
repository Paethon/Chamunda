package com.gmail.chamoners.chamunda

import Preamble._
import scala.collection.JavaConverters._
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.entity.Creature
import collection.mutable.HashSet
import org.bukkit.entity.Entity
import de.ntcomputer.minecraft.controllablemobs.api.ControllableMobs
import de.ntcomputer.minecraft.controllablemobs.api.ai.behaviors.AIAttackMelee

class MobController(env: Environment) {

  val controlledEntities = HashSet.empty[Entity]

  private def control {
    if (env.world.getPlayers.size != 0) {
      val entities = env.world.getEntities().asScala
      val player = env.world.getPlayers.get(0)
      for (e <- entities) {
        if (!controlledEntities.contains(e) && e.getType == EntityType.ZOMBIE) {
          controlledEntities += e
          val le = e.asInstanceOf[LivingEntity]
          val ce = ControllableMobs.getOrAssign(le, true)
          ce.getAttributes().getMovementSpeedAttribute().setBasisValue(0.4)
          ce.getActions().target(player)
          ce.getActions().follow(player)
        }
      }
    }
  }

  def attach {
    env.execute(20){control}
    env.log.info("MobController attached")
  }
}