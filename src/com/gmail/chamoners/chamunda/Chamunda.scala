package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger
import scala.language.implicitConversions
import Preamble._

class Chamunda extends JavaPlugin {
  var log: Logger = null

  override def onEnable {
    log = Logger.getLogger("Minecraft")
    log.info("Scala Enabled!")

    val pm = this.getServer().getPluginManager()
    pm.registerEvents(new ListenerHandler(this), this)
  }

  override def onDisable {

  }

}