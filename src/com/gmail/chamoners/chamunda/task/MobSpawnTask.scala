package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Village
import org.bukkit.entity.EntityType
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Zeit
import scala.collection.JavaConverters._

class MobSpawnTask(env: Environment, vill: Village) {
  import EntityType._
  val monsterList = List(ZOMBIE, SKELETON, SPIDER)
  def randomMonster = monsterList(util.Random.nextInt(monsterList.length))
  
  private def timetick {
    if (env.world.getPlayers().size() > 0)
      vill.mobspawn.spawn(randomMonster)
  }

  def attach {
    env.execute(40) { timetick }
    env.log.info("MobSpawnTask attached")
  }
}