package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Village
import org.bukkit.entity.EntityType
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Zeit
import scala.collection.JavaConverters._

class MobSpawnTask(env: Environment) {
  import EntityType._
  val monsterProb = Map(ZOMBIE -> 0.5, SKELETON -> 0.3, SILVERFISH -> 0.2)
  val monsterList = monsterProb.keys.toList
  def randomMonster = {
    var monster: EntityType = null
    do {
      monster = monsterList(util.Random.nextInt(monsterList.length))
    } while (util.Random.nextDouble > monsterProb(monster))
    monster
  }

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