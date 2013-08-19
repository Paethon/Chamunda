package com.gmail.chamoners.chamunda

import org.bukkit.plugin.java.JavaPlugin

case class Environment(plugin:JavaPlugin) {
  val server = plugin.getServer
  val world = server.getWorlds.get(0)
  
}