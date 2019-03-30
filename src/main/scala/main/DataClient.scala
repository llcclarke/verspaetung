package main

import scala.io.Source


class DataClient {

  val delaysFromCsv = Source.fromFile("./data/delays.csv").getLines().drop(1)

  val delays = delaysFromCsv.flatMap(response => response.split(",") match {
    case Array(lineName, delayInMinutes) => Some(lineName -> delayInMinutes)
    case _ => None
  }).toMap

  def getDelays(routeName: String): Option[String] = delays.get(routeName)

}
