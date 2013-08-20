package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Preamble._
import com.gmail.chamoners.chamunda.Environment
import scala.collection.JavaConverters._
import org.bukkit.entity.LivingEntity
import org.bukkit.Material
import com.gmail.chamoners.chamunda.BlockHealth


class MobBlockController(implicit env: Environment) {
  
  val blockHealth = new BlockHealth(env)
  
  def control {
    val entities = env.world.getEntities().asScala
    for(e <- entities; if e.isInstanceOf[LivingEntity]) {
      val le = e.asInstanceOf[LivingEntity]
      le.blockToAttack match {
        case Some(b) => blockHealth.attack(b)
        case None =>
      }
    }
  }
  
  def attach {
    env.execute(5) { control }
    env.log.info("MobBlockController attached")
  }
}