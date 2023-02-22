package net.skm

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID
import scala.util.Try

object Domain {

  case class Date private(value: LocalDate)

  object Date {
    val datePattern = "dd/MM/yyyy"

    def fromString(str: String): Option[Date] =
      Try(LocalDate.parse(str, DateTimeFormatter.ofPattern(datePattern))).map(Date(_)).toOption
  }

  case class Task(id: UUID, description: String, startDate: Date, deadline: Date) {
    override def toString: String = s"{ id: $id , description: $description , start: $startDate , deadline: $deadline }"
  }

  sealed trait Command

  object Command {
    case object Insert extends Command

    case object View extends Command

    case object Remove extends Command

    case object Exit extends Command

    def fromString(str: String): Option[Command] =
      Option(str).filter(_.trim.nonEmpty).map(_.trim.toLowerCase) match {
        case Some("insert") => Some(Insert)
        case Some("view") => Some(View)
        case Some("delete") => Some(Remove)
        case Some("exit") => Some(Exit)
        case _ => None
      }
  }
}
