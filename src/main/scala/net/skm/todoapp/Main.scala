package net.skm.todoapp

import net.skm.todoapp.CommandParser._
import net.skm.todoapp.Domain.Task

import java.util.UUID
import scala.collection.mutable

object Main extends App {

  val todos = mutable.Map.empty[UUID, Task]
  startup(todos)

}
