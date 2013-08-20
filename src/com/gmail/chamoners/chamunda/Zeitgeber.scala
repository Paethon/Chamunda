package com.gmail.chamoners.chamunda

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

//object Zeit extends Enumeration {
//  type Zeit = Integer
//  val Dawn = 22900
//  val Day = 23450
//  val Dusk = 12000
//  val Night = 13100
//}

class ZeitgeberEvent(val zeit: Zeit) extends Event() {
  import ZeitgeberEvent._

  def getZeit(): Zeit = zeit
  def getHandlers(): HandlerList = handlers

}

object ZeitgeberEvent {
  val handlers = new HandlerList()
  def getHandlerList(): HandlerList = handlers

}