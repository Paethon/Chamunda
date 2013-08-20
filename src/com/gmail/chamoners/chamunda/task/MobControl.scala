package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Preamble._
import scala.collection.JavaConverters._
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import collection.mutable.HashSet
import org.bukkit.entity.Entity
import de.ntcomputer.minecraft.controllablemobs.api.ControllableMobs
import com.gmail.chamoners.chamunda.Environment

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
          val ce = ControllableMobs.getOrAssign(le)
          //          ce.getAttributes().getMovementSpeedAttribute().setBasisValue(0.4)
          val rndPlayer = env.randomPlayer
          ce.getActions().target(rndPlayer)
          ce.getActions().follow(rndPlayer)
        }
      }
    }
  }

  def attach {
    env.execute(20) { control }
    env.log.info("MobController attached")
  }
}