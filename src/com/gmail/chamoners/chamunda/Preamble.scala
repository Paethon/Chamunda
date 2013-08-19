package com.gmail.chamoners.chamunda

import scala.language.implicitConversions

object Preamble {
  //Found at
  //http://stackoverflow.com/questions/3073677/implicit-conversion-to-runnable
  //Still learning Scala, not sure how to pass a closure
  implicit def runnable(f: ⇒ Any): Runnable = new Runnable() { def run() = f }
}