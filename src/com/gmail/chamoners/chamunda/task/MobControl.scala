package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Preamble._
import scala.collection.JavaConverters._
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import collection.mutable.HashSet
import org.bukkit.entity.Entity
import de.ntcomputer.minecraft.controllablemobs.api.ControllableMobs
import com.gmail.chamoners.chamunda.Environment
import org.bukkit.entity.Monster

class MobController(env: Environment) {

  val controlledEntities = HashSet.empty[Entity]

  private def control {
    import EntityType._
    if (env.world.getPlayers.size != 0) {
      val entities = env.world.getEntities().asScala
      val player = env.world.getPlayers.get(0)
      for (e <- entities) {
        if (!controlledEntities.contains(e) && e.isInstanceOf[Monster]) {
          controlledEntities += e
          val le = e.asInstanceOf[LivingEntity]
          val ce = ControllableMobs.getOrAssign(le)

          e.getType match {
            case ZOMBIE =>
              // ce.getAttributes().getMovementSpeedAttribute().setBasisValue(0.4)
              env.randomVillager match {
                case Some(rndVillager) =>
                  ce.getActions().target(rndVillager)
                  ce.getActions().follow(rndVillager)
                case None => 
                  e.remove()
                  env.log.info("Removed zombie since no villager there to attach to")
              }

            case SKELETON | SPIDER =>
              env.randomPlayer match {
                case Some(rndPlayer) =>
                  ce.getActions().target(rndPlayer)
                  ce.getActions().follow(rndPlayer)
                case None => 
                  e.remove
                  env.log.info("Removed monster since no player there to attach to")
              }
            case _ =>
          }
        }
      }
    }
  }

  def attach {
    env.execute(20) { control }
    env.log.info("MobController attached")
  }
}