package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import scala.language.implicitConversions
import Preamble._
import org.bukkit.command._
import org.bukkit.entity.Player
import org.bukkit.entity.CreatureType

class Chamunda extends JavaPlugin {

  lazy val log = Logger.getLogger("Minecraft")

  override def onEnable {
    log.info("Scala Enabled!")

    val pm = this.getServer().getPluginManager()
    pm.registerEvents(new ListenerHandler(this), this)
  }

  override def onCommand(sender: CommandSender, cmd: Command, label: String, args: Array[String]) = {
    if (sender.isInstanceOf[Player]) {
      val p = sender.asInstanceOf[Player]

      cmd.getName.toLowerCase match {
        case "test" =>
          val spawner = new MobSpawner(p.getLocation(), 20, 10, 10, this)
          for (i <- 1 to 100)
            spawner.spawn(CreatureType.ZOMBIE)
          true
        case "a" =>
          p.sendMessage("echo a")
          true
        case "b" =>
          p.sendMessage("echo b")
          true
        case _ => false
      }
    } else
      false
  }

  override def onDisable {

  }

}