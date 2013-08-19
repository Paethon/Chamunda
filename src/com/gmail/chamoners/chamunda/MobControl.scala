package com.gmail.chamoners.chamunda

import Preamble._
import scala.collection.JavaConverters._
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity

/*
def test() {
    server.getScheduler().scheduleSyncRepeatingTask(plugin, { println("test") }, 0, 1)
  }
 */

class MobController(env:Environment) {
  private def control {
    val entities = env.world.getEntities().asScala
    for(e <- entities; if(e.getType == EntityType.ZOMBIE)) {
      val le = e.asInstanceOf[LivingEntity]
      le.remove()
    }
  }
  
  def attach {
    env.server.getScheduler().scheduleSyncRepeatingTask(env.plugin, control, 0, 100)
    env.log.info("MobController attached")
  }
}