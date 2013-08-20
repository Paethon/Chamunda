package com.gmail.chamoners.chamunda

import com.gmail.chamoners.chamunda.MobSpawner
import scala.collection.mutable.HashSet
import org.bukkit.entity.Villager
import scala.collection.JavaConverters._

class Village(val c: Point, val width: Int, val height: Int, env: Environment) {
  import env._

  lazy val mobspawn = new MobSpawner(c, width + 20, height + 20, width, height, env)
  lazy val villagers = initVillagers

  /**
   * *
   * Ugly init
   */
  def initVillagers: HashSet[Villager] = {
    val tmp = HashSet.empty[Villager]
    for (lv <- world.getLivingEntities().asScala)
      if (lv.isInstanceOf[Villager])
        tmp.add(lv.asInstanceOf[Villager])

    server.broadcastMessage("Villagers initialized ")

    return tmp
  }

}

object Village {
  def apply(c: Point, width: Int, height: Int, env: Environment) = {
    new Village(c: Point, width: Int, height: Int, env: Environment)
  }

  def apply(from: Point, to: Point, env: Environment) = {
    val diff = (from - to).abs
    val center = (from + to) / 2

    new Village(center, diff.x, diff.z, env: Environment)
  }
}