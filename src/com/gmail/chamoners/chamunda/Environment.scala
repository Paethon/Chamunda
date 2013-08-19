package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Logger

case class Environment(plugin:JavaPlugin) {
  val server = plugin.getServer
  val world = server.getWorlds.get(0)
  val log = Logger.getLogger("Minecraft")
}