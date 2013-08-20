package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Village
import org.bukkit.entity.EntityType
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Zeit
import scala.collection.JavaConverters._

class MobSpawnTask(env: Environment) {
  import env._
  private def timetick {
    if (zeit == Zeit.Night)
      if (world.getPlayers().size() > 0) {
        for (p <- world.getPlayers().asScala)
          vill.mobspawn.spawn(EntityType.ZOMBIE)
      }

  }

  def attach {
    execute(10) { timetick }
    log.info("MobSpawnTask attached")
  }
}