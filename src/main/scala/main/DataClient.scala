package main

import org.joda.time.LocalTime
import org.joda.time.format.DateTimeFormat

import scala.io.Source


class DataClient {

  val fmt = DateTimeFormat.forPattern("HH:mm:ss")


  private val delaysFromCsv = Source.fromFile("./data/delays.csv").getLines().drop(1)

  private val delays = delaysFromCsv.flatMap(
    response => response.split(",") match {
      case Array(lineName, delayInMinutes) => Some(lineName -> delayInMinutes)
      case _ => None
    }).toMap

  def getDelays(routeName: String): Option[String] = delays.get(routeName)

  private val arrivalsFromCsv = Source.fromFile("./data/times.csv").getLines().drop(1)



  private def arrivals = arrivalsFromCsv.flatMap(
    response => response.split(",") match {
      case Array(lineId, stopId, times) => Some((stopId, fmt.parseLocalTime(times), lineId))
      case _ => None
    }
  ).toList

  def getArrivals(stopId: String, currentTime: LocalTime): Option[String] = {

    val onlyRelevantStops =
      arrivals.filter(_._1 == stopId)

    if (onlyRelevantStops.filter(_._2.isAfter(currentTime)).isEmpty) {
      None
    }
    else {
      findLineName(onlyRelevantStops.filter(_._2.isAfter(currentTime)).head._3)
    }
  }



  private val linesFromCsv = Source.fromFile("./data/lines.csv").getLines().drop(1)

  private def lines = linesFromCsv.flatMap(
    response => response.split(",") match {
      case Array(lineId, lineName) => Some(lineId -> lineName)
      case _ => None
    }
  ).toMap

  private def findLineName(lineId: String) = {
    lines.get(lineId)

  }


}

