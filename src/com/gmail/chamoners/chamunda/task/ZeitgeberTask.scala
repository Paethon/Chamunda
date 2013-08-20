package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.ZeitgeberEvent
import com.gmail.chamoners.chamunda.Zeit

class ZeitgeberTask(env: Environment) {

  import env._
  private def timetick {
    val time = env.world.getTime()
    if (zeit == Zeit.Day && time < Zeit.Day.getTime() && time >= Zeit.Dusk.getTime()) changeZeit(Zeit.Dusk)
    if (zeit == Zeit.Dusk && time >= Zeit.Night.getTime()) changeZeit(Zeit.Night)
    if (zeit == Zeit.Night && time >= Zeit.Dawn.getTime()) changeZeit(Zeit.Dawn)
    if (zeit == Zeit.Dawn && time >= Zeit.Day.getTime()) changeZeit(Zeit.Day)

    env.server.broadcastMessage(env.world.getTime() + " " + zeit)
  }

  def attach {
    env.execute(100) { timetick }
    env.log.info("Zeitgeber attached")
  }
}