package com.gmail.chamoners.chamunda

import scala.language.implicitConversions

object Preamble {
  implicit def runnable(f: ⇒ Any): Runnable = new Runnable() { def run() = f }
}