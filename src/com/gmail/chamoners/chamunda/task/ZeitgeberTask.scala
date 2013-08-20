package com.gmail.chamoners.chamunda.task

import com.gmail.chamoners.chamunda.Environment

class ZeitgeberTask(env: Environment) {
  private var lastTime: Long = "99999999999999".toLong
  private def timetick {

    //    env.world.getTime()
    //    env.server.broadcastMessage(env.world.getTime() + " ")
    //    env.plugin.
  }

  def attach {
    env.execute(1) { timetick }
    env.log.info("Zeitgeber attached")
  }
}