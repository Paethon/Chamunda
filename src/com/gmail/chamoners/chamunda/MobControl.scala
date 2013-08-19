package com.gmail.chamoners.chamunda

import Preamble._
import org.bukkit.plugin.java.JavaPlugin

/*
def test() {
    server.getScheduler().scheduleSyncRepeatingTask(plugin, { println("test") }, 0, 1)
  }
 */

object MobControl {
  private def control {
    
  }
  
  def attach(plugin:JavaPlugin) {
    val server = plugin.getServer
    server.getScheduler().scheduleSyncRepeatingTask(plugin, control, 0, 1)
  }
}