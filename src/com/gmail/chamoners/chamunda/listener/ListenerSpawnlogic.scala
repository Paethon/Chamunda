package com.gmail.chamoners.chamunda.listener

import org.bukkit.event.Listener
import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.MobSpawner
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.EventHandler
import org.bukkit.entity.EntityType
import org.bukkit.entity.Monster
import org.bukkit.event.entity.EntityDamageByBlockEvent
import org.bukkit.event.entity.EntityDamageEvent.DamageCause
import com.gmail.chamoners.chamunda.Village
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.EntityChangeBlockEvent
import org.bukkit.event.entity.EntityInteractEvent

case class ListenerSpawnlogic(env: Environment, vill: Village) extends Listener {

  val stdRad = 5

  @EventHandler
  def mobDeath(event: EntityDeathEvent) = {
    val entity = event.getEntity()
    if (entity.isInstanceOf[Monster]) {
      entity.getLastDamageCause().getCause() match {
        case DamageCause.CONTACT     => vill.mobspawn.demote(entity, stdRad, 0.80)
        case DamageCause.DROWNING    => vill.mobspawn.demote(entity, stdRad, 0.80)
        case DamageCause.FALL        => vill.mobspawn.demote(entity, stdRad, 0.80)
        case DamageCause.LAVA        => vill.mobspawn.demote(entity, stdRad, 0.80)
        case DamageCause.SUFFOCATION => vill.mobspawn.demote(entity, stdRad, 0.80)
        case _                       =>
      }
    }

    vill.mobspawn.removeCreature(entity) //Stop tracking
  }

  @EventHandler
  def mobDmg(event: EntityDamageByEntityEvent) = {
    if (event.getDamager().isInstanceOf[LivingEntity] && !event.getEntity().isInstanceOf[Monster] && event.getDamager().isInstanceOf[Monster]) {
      vill.mobspawn.promote(event.getDamager().asInstanceOf[LivingEntity], 10, 1.2)
    }
  }

  @EventHandler
  def blockDmgByMob(event: EntityInteractEvent) = {
    env.server.broadcastMessage(event.getEventName())
  }
}