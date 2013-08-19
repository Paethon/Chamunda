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

class Chamunda extends JavaPlugin {

  lazy val log = Logger.getLogger("Minecraft")
  lazy val env = Environment(this)
  lazy val mobControl = new MobController(env)

  override def onEnable {
    //Listener init
    val pm = this.getServer().getPluginManager()
    pm.registerEvents(listener.ListenerSpawnlogic(env), this)

    //Scheduler init
    mobControl.attach

    //Create TestVillage
    val v = Village(Point(-149, 647), Point(-94, 711))
    val vc = v.c(env.world)
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
          val spawner = new MobSpawner(p.getLocation(), 20, 10, 10, Environment(this))
          for (i <- 1 to 100)
            spawner.spawn(EntityType.ZOMBIE)
          true
        case "a" =>
          val hs = new HashSet[java.lang.Byte]()
          hs.add((0x00).byteValue)
          val block = p.getTargetBlock(hs, 100)
          p.sendMessage(block.getType.toString)
          true
        case "b" =>
          p.sendMessage("echo b")
          true
        case "kill" =>
          for (lv <- env.world.getLivingEntities().asScala)
            if (lv.isInstanceOf[Monster])
              lv.remove()
          true
        case _ => false
      }
    } else
      false
  }

}