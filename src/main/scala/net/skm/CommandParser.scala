package net.skm

import net.skm.Main.todos

import java.util.UUID
import scala.annotation.tailrec
import scala.io.StdIn
import scala.util.{Failure, Success, Try}
import Domain._
import scala.collection.mutable

object CommandParser {

  @tailrec
  def startup(todos: mutable.Map[UUID, Task]): Unit = {
    promptCommand("specify command [ insert | list | delete | exit ]") match {
      case Some(Command.Insert) =>
        promptInsert()
        startup(todos)
      case Some(Command.View) =>
        todos.values.foreach(println)
        startup(todos)
      case Some(Command.Remove) =>
        promptDelete(todos)
        startup(todos)
      case Some(Command.Exit) => println("application terminated")
      case Some(_) | None => startup(todos)
    }
  }

  @tailrec
  def promptDelete(todos: mutable.Map[UUID, Task]): Unit = prompt("specify id of task to delete or exit") match {
    case Some("exit") =>
    case Some(value) =>
      Try(UUID.fromString(value)) match {
        case Failure(_) =>
          println("Invalid UUID")
          promptDelete(todos)
        case Success(value) =>
          todos.get(value) match {
            case Some(task) => todos.remove(task.id)
            case None =>
              println(s"id $value not found")
              promptDelete(todos)
          }
      }
    case None => promptDelete(todos)
  }

  @tailrec
  def promptInsert(): Unit = prompt("specify description or exit") match {
    case Some("exit") =>
    case Some(desc) =>
      promptDate("start") match {
        case Some(startDate) =>
          promptDate("end") match {
            case Some(endDate) =>
              val task = Task(UUID.randomUUID(), desc, startDate, endDate)
              todos.addOne(task.id -> task)
              println("Task inserted correctly")
            case None => println("application terminated")
          }
        case None => println("application terminated")
      }
    case None =>
      println("description cannot be an empty string")
      promptInsert()
  }

  def prompt(message: String): Option[String] =
    Option(StdIn.readLine(s">> $message \n")).filter(_.trim.nonEmpty).map(_.trim.toLowerCase)

  def promptCommand(message: String): Option[Command] = prompt(message).flatMap(Command.fromString)

  @tailrec
  def promptDate(label: String): Option[Date] = prompt(s"specify $label date [${Date.datePattern}] or exit") match {
    case Some("exit") => None
    case Some(value) =>
      Date.fromString(value) match {
        case None =>
          println("Invalid date.")
          promptDate(label)
        case Some(date) => Some(date)
      }
    case None => promptDate(label)
  }

}
