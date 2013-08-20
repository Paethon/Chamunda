package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Village
import org.bukkit.entity.EntityType
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Zeit
import scala.collection.JavaConverters._

class MobSpawnTask(env: Environment) {
  import EntityType._
  val monsterList = List(ZOMBIE, SKELETON, SILVERFISH)
  def randomMonster = monsterList(util.Random.nextInt(monsterList.length))

  private def timetick {
    if (env.zeit == Zeit.Night)
      for (v <- 0 until env.world.getPlayers().size())
        if (env.world.getPlayers().size() > 0)
          env.vill.mobspawn.spawn(randomMonster)
  }

  def attach {
    env.execute(80) { timetick }
    env.log.info("MobSpawnTask attached")
  }
}