package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Village
import org.bukkit.entity.EntityType

class MobSpawnTask(env: Environment, vill: Village) {
  private def timetick {
    if (env.world.getPlayers().size() > 0)
      vill.mobspawn.spawn(EntityType.ZOMBIE)

  }

  def attach {
    env.execute(10) { timetick }
    env.log.info("MobSpawnTask attached")
  }
}