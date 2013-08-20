package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Environment
import com.gmail.chamoners.chamunda.Zeit

class ZeitgeberTask(env: Environment) {
  private var lastTime: Long = "99999999999999".toLong
  private var zeit = Zeit.Day
  private def timetick {

    //    env.world.getTime()
    env.server.broadcastMessage(env.world.getTime() + " ")
    //        env.plugin.
  }

  //  private def 

  def attach {
    env.execute(100) { timetick }
    env.log.info("Zeitgeber attached")
  }
}