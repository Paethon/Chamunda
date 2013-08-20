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
  implicit lazy val env = Environment(this)

  //Tasks
  lazy val mobControl = new MobController(env)
  lazy val mobBlockControl = new MobBlockController
  lazy val zeitgeberTask = new ZeitgeberTask(env)
  lazy val mobSpawnTask = new MobSpawnTask(env)

  override def onEnable {
    onChamundaEnable
  }

  override def onDisable {

  }

  def onChamundaEnable {

    //Scheduler init
    mobControl.attach
    mobBlockControl.attach
    zeitgeberTask.attach
    mobSpawnTask.attach

    //Create TestVillage
    this.getServer().getPluginManager().registerEvents(ListenerSpawnlogic(env), this)
    env.changeZeit(Zeit.Night)
  }

  def onChamundaDisable {

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
          p.sendMessage(env.zeit + " ")
          true
        case "b" =>
          env.vill.mobspawn.writeProbability("C:\\mob.png")
          true
        case "start" =>
          onChamundaEnable
          true
        case "nuke" =>
          for (lv <- env.world.getLivingEntities().asScala)
            if (lv.isInstanceOf[Monster])
              lv.setHealth(0)

          p.sendMessage("KABOOOOM!")
          env.world.playEffect(p.getLocation(), Effect.BLAZE_SHOOT, 0)
          true
        case _ => false
      }
    } else
      false
  }

}