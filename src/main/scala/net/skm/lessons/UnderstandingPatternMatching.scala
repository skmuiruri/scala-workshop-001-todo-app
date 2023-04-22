package net.skm.lessons

import scala.util.{Failure, Success, Try}

/*

     (                                         *
     )\ )          )    )                    (  `                       )
    (()/(    )  ( /( ( /(   (   (            )\))(      )  (         ( /( (          (  (
     /(_))( /(  )\()))\()) ))\  )(    (     ((_)()\  ( /(  )(    (   )\()))\   (     )\))(
    (_))  )(_))(_))/(_))/ /((_)(()\   )\ )  (_()((_) )(_))(()\   )\ ((_)\((_)  )\ ) ((_))\
    | _ \((_)_ | |_ | |_ (_))   ((_) _(_/(  |  \/  |((_)_  ((_) ((_)| |(_)(_) _(_/(  (()(_)
    |  _// _` ||  _||  _|/ -_) | '_|| ' \)) | |\/| |/ _` || '_|/ _| | ' \ | || ' \))/ _` |
    |_|  \__,_| \__| \__|\___| |_|  |_||_|  |_|  |_|\__,_||_|  \__| |_||_||_||_||_| \__, |
                                                                                    |___/
/*
  ==============
    SESSION I
  ==============
 */
*/
object UnderstandingPatternMatching extends App {

  case class Ordinazione(nomeCliente: String, telefono: String, tipoCibo: String, descCibo: String,
                         bevanda: String, statoOrdine: String, dataOrdine: String)

  val ordineMattia =
    Ordinazione("mattia", "23435454", "pizza", "margherita", "fanta", "in elaborazione", "02/02/2023")
  val ordineFabio =
    Ordinazione("fabio", "23435454", "pasta", "tortellini", "acqua", "in elaborazione", "02/02/2023")
  val ordineErnesto =
    Ordinazione("ernesto", "23435454", "riso", "risotto", "acqua", "in elaborazione", "02/02/2023")
  val ordineKevin =
    Ordinazione("kevin", "2124245", "carne", "carne", "pepsi", "erogato", "03/04/2023")

  def stampaOrdine(ordine: Ordinazione) = {
    if (ordine.tipoCibo == "pizza")
      println(ordine.nomeCliente + " ha ordinato la pizza, " + ordine.descCibo)
    else if (ordine.tipoCibo == "pasta")
      println(ordine.nomeCliente + " ha ordinato la pasta, " + ordine.descCibo)
    else if (ordine.tipoCibo == "riso")
      println(ordine.nomeCliente + " ha ordinato il riso, " + ordine.descCibo)
    else println("il tipo cibo " + ordine.tipoCibo + " ordinato da " + ordine.nomeCliente + " non previsto")
  }

  // constructor pattern
  def stampaOrdine2(ordine: Ordinazione) = {
    ordine match {
      case Ordinazione(cliente, _, "pizza", desc, _, _, _) =>
        println(cliente + " ha ordinato la pizza, " + desc)
      case Ordinazione(nomeCliente, _, "pasta", descCibo, _, _, _) =>
        println(nomeCliente + " ha ordinato la pasta, " + descCibo)
      case Ordinazione(nomeCliente, telefono, "riso", descCibo, bevanda, statoOrdine, dataOrdine) =>
        println(nomeCliente + " ha ordinato il riso, " + descCibo)
      case Ordinazione(nomeCliente, telefono, tipoCibo, descCibo, bevanda, statoOrdine, dataOrdine) =>
        println("il tipo cibo " + tipoCibo + " ordinato da " + nomeCliente + " non previsto")
    }
  }

  //stampaOrdine2(ordineFabio)
  // ....
  /*
    ==============
      SESSION II
    ==============
   */

  Villaggio("fabio's village")
    .creaEsercito()
    .flatMap(_.attack)
    .flatMap(_.schiavizzare)
    .flatMap(_.lavora)

  // for-comprehension
  for {
    villaggio    <- Right(Villaggio("villaggio di Fabio"))
    esercito     <- villaggio.creaEsercito()
    popolazione  <- esercito.attack
    ps           <- popolazione.schiavizzare
    stato        <- ps.lavora
  } yield stato

  case class Villaggio(name: String) {
    def creaEsercito(): Either[Exception, Esercito] = Right(Esercito("esercito di Fabio"))
  }

  case class AttackingWithoutAnArmy() extends Exception

  val success: Either[AttackingWithoutAnArmy, String] = Right("successo")
  val failure: Either[Exception, String] = Left(new Exception("errore"))

  def fun: Try[String] = Success("successo")
  def fun2: Try[String] = Failure(new Exception("failed"))

  // Either, Try, Option
  // Monad
  // Some(esercito), None
  def getEsercito: Option[Esercito] = None

  def attackNeighbour(esercito: Esercito) = esercito.attack

  case class Esercito(name: String) {
    def attack: Either[Exception, Popolazione] = Right(Popolazione("popolazione sconfitto"))
  }

  case class Popolazione(name: String) {
    def schiavizzare:Either[Exception, PopoloSottoMesso] = Right(PopoloSottoMesso())
  }

  case class PopoloSottoMesso() {
    def lavora: Either[Exception, String] = Right("poppo sottomesso sta lavorando")
  }



}