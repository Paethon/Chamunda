package com.gmail.chamoners.chamunda.listener

import org.bukkit.event.Listener
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.MobSpawner
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.EventHandler
import org.bukkit.entity.EntityType
import org.bukkit.entity.Monster

case class ListenerSpawnlogic(env: Environment) extends Listener {

  @EventHandler
  def mobDeath(event: EntityDeathEvent) = {
    if (event.getEntity().isInstanceOf[Monster]) {
      //      if (event.)
      //      env.server.broadcastMessage("quak")
    }

  }
  //  val ms = MobSpawner.m
}