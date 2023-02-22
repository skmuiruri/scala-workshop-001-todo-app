package net.skm

import net.skm.CommandParser._
import net.skm.Domain.Task

import java.util.UUID
import scala.collection.mutable

object Main extends App {

  val todos = mutable.Map.empty[UUID, Task]
  startup(todos)

}
