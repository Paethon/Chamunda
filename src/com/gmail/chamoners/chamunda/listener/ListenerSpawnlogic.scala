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
import org.bukkit.event.entity.CreatureSpawnEvent
import com.gmail.chamoners.chamunda.Point
import com.gmail.chamoners.chamunda.MobState
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import com.gmail.chamoners.chamunda.ZeitgeberEvent
import com.gmail.chamoners.chamunda.Zeit
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.Location
import org.bukkit.entity.Villager
import org.bukkit.inventory.ItemStack
import org.bukkit.Material
import org.bukkit.event.player.PlayerRespawnEvent
import scala.collection.JavaConverters._

case class ListenerSpawnlogic(env: Environment) extends Listener {
  import env._

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
    } else if (entity.isInstanceOf[Villager]) {
      val v = entity.asInstanceOf[Villager]
      if (vill.villagers.contains(v)) {
        vill.villagers.remove(v)
        server.broadcastMessage(vill.villagers.size + " villagers remaining")

        if (vill.villagers.size <= 0) { // End Game
          for (p <- world.getPlayers().asScala) {
            p.sendMessage("You lost!")
            p.setHealth(0.0)
          }
        }
      }
    }

    vill.mobspawn.removeCreature(entity) //Stop tracking
  }

  @EventHandler
  def mobDmg(event: EntityDamageByEntityEvent) = {
    if (event.getDamager().isInstanceOf[Monster] && !event.getEntity().isInstanceOf[Monster]) {
      vill.mobspawn.promote(event.getDamager().asInstanceOf[LivingEntity], 10, 1.2)
    }
  }

  @EventHandler
  def blockDmgByMob(event: EntityInteractEvent) = {
    server.broadcastMessage(event.getEventName())
  }

  @EventHandler
  def mobSpawn(event: CreatureSpawnEvent) = {
    val entity = event.getEntity()
    if (entity.isInstanceOf[Monster]) {
      //      server.broadcastMessage(event.getSpawnReason().toString())
      if (event.getSpawnReason() == SpawnReason.NATURAL)
        event.setCancelled(true)
      //        true
      //vill.mobspawn.addCreature(mob, spawnPoint)
      //      val l = event.getLocation()
      //      vill.mobspawn.mobStates += (entity -> MobState(Point(l.getBlockX(), l.getBlockZ())))
    }
  }

  @EventHandler
  def zeitChange(event: ZeitgeberEvent) = {
    event.getZeit match {
      case Zeit.Dawn  => executeOnce(400) { changeZeit(Zeit.Day) }
      case Zeit.Day   => executeOnce(1200) { changeZeit(Zeit.Dusk) }
      case Zeit.Dusk  => executeOnce(400) { changeZeit(Zeit.Night) }
      case Zeit.Night => executeOnce(2800) { changeZeit(Zeit.Dawn) }
      case _          =>
    }

    server.broadcastMessage(">> " + event.getZeit + " ")
  }

  @EventHandler
  def playerJoin(event: PlayerJoinEvent) = {
    val l = vill.c(world)
    l.setY(world.getHighestBlockYAt(vill.c(world)) + 3)

    event.getPlayer().teleport(l)
    //    event.getPlayer().setBedSpawnLocation(l)

    event.getPlayer().getInventory().clear()
    event.getPlayer().getInventory().addItem(new ItemStack(Material.STONE_SWORD, 1))
    //    event.getPlayer().sendMessage("hey thre")
    //    server.broadcastMessage("ASD")
  }

  @EventHandler
  def playerRespawn(event: PlayerRespawnEvent) = {
    val l = vill.c(world)
    l.setY(world.getHighestBlockYAt(vill.c(world)) + 3)
    event.setRespawnLocation(l)
  }
}