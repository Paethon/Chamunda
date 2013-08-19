package com.gmail.chamoners.chamunda

import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.block.Block
import org.bukkit.block.Sign
import org.bukkit.ChatColor
import org.bukkit.event.block.Action
import org.bukkit.Material
import org.bukkit.event.EventHandler
import scala.language.implicitConversions
import Preamble._
// From https://github.com/nmarshall23/scalaBukkitPlugin/tree/master/src/main/scala/com/gmail/nmarshall23/demo
class ListenerHandler(plugin: Chamunda) extends Listener {
  @EventHandler
  def getSignPlacement(event: PlayerInteractEvent) = {
    event match {
      case event if event.isCancelled() ⇒
        Unit
      case event if event.hasBlock() == false ⇒
        Unit
      case event if event.getAction() != Action.RIGHT_CLICK_BLOCK ⇒
        Unit
      case event if event.getClickedBlock().getType() != Material.WALL_SIGN ⇒
        Unit
      case event ⇒
        event.setCancelled(true)
        signPlacement(event.getClickedBlock())
    }
  }

  def signPlacement(b: Block): Unit = {

    plugin.getServer().broadcastMessage("Sign Clicked!")
    var sign = b.getState().asInstanceOf[Sign]

    val line = ChatColor.DARK_RED + sign.getLine(0)
    sign.setLine(0, line)

    // I know there is a better way.
    plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin,
      {sign.update()}, 1)
  }
}