package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import scala.language.implicitConversions
import Preamble._
import org.bukkit.command._
import org.bukkit.entity.Player
import org.bukkit.entity.CreatureType
import org.bukkit.entity.EntityType
import java.util.HashSet
import org.bukkit.Material
import scala.collection.JavaConverters._
import org.bukkit.entity.Monster
import org.bukkit.Effect
import com.gmail.chamoners.chamunda.task.MobController
import com.gmail.chamoners.chamunda.task.ZeitgeberTask
import com.gmail.chamoners.chamunda.task.MobSpawnTask
import com.gmail.chamoners.chamunda.listener._
import com.gmail.chamoners.chamunda.task.MobBlockController

class Chamunda extends JavaPlugin {

  lazy val log = Logger.getLogger("Minecraft")
  lazy val env = Environment(this)

  //Tasks
  lazy val mobControl = new MobController(env)
  lazy val mobBlockControl = new MobBlockController(env)
  lazy val zeitgeberTask = new ZeitgeberTask(env)
  lazy val mobSpawnTask = new MobSpawnTask(env, vill)

  var vill: Village = null

  override def onEnable {
    vill = Village(Point(-149, 647), Point(-94, 711), env)

    //Listener init
    val pm = this.getServer().getPluginManager()

    //Scheduler init
    //mobControl.attach
    mobBlockControl.attach
    zeitgeberTask.attach
    mobSpawnTask.attach

    //Create TestVillage
    pm.registerEvents(ListenerSpawnlogic(env, vill), this)

    val vc = vill.c(env.world)
    vc.setY(env.world.getHighestBlockAt(vc).getY())
    vc.getBlock().setType(Material.DIAMOND_BLOCK)

  }

  override def onDisable {

  }

  override def onCommand(sender: CommandSender, cmd: Command, label: String, args: Array[String]) = {
    if (sender.isInstanceOf[Player]) {
      val p = sender.asInstanceOf[Player]

      cmd.getName.toLowerCase match {
        case "test" =>
          val spawner = new MobSpawner(p.getLocation(), 20, 20, 10, 10, Environment(this))
          for (i <- 1 to 100)
            spawner.spawn(EntityType.ZOMBIE)
          true
        case "a" =>
          val hs = new HashSet[java.lang.Byte]()
          p.blockInFront match {
            case Some(x) => p.sendMessage(x.getType.toString)
            case None => p.sendMessage("No block")
          }
          
          true
        case "b" =>
          vill.mobspawn.writeProbability("C:\\mob.png")
          true
        case "nuke" =>
          for (lv <- env.world.getLivingEntities().asScala)
            if (!lv.isInstanceOf[Player])
              env.world.createExplosion(lv.getLocation(), 5)

          p.sendMessage("KABOOOOM!")
          env.world.playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 0)
          true
        case _ => false
      }
    } else
      false
  }

}