package com.gmail.chamoners.chamunda

import org.bukkit.event.Event
import org.bukkit.event.HandlerList

object Zeitgeber {

  def isNight(t: Long): Boolean = {
    true
  }

  def isDay(t: Long): Boolean = {
    true
  }

}

object Zeit extends Enumeration {
  type Zeit = Integer
  val Dawn = 1000
  val Day = 12000
  val Dusk = 213123
  val Night = 23123
}

class ZeitgeberEvent extends Event() {
  import ZeitgeberEvent._

  var message: String = null
  def CustomEvent(example: String) = message = example
  def getMessage(): String = message
  def getHandlers(): HandlerList = handlers

}

object ZeitgeberEvent {
  val handlers = new HandlerList()
  def getHandlerList(): HandlerList = handlers

}