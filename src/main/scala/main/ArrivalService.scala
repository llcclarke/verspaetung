package main

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.Future
import org.joda.time.LocalTime
import play.api.libs.json.{JsValue, Json, Writes}


class ArrivalService(dataClient: DataClient) {


  def getArrivals(stopId: String, currentTime: LocalTime) = new Service[Request, Response] {

    def apply(req: Request): Future[Response] = {
      dataClient.getArrivals(stopId, currentTime) match {
        case Some(a) =>
          jsonResponse(Status.Ok, Json.toJson(Arrivals(a)))
          val response = Response(Status.Ok)
          response.setContentString(a)
          response.setContentTypeJson()
          Future.value(response)
        case _ =>
          val response = Response(Status.NotFound)
          response.setContentString("No more vehicles due today")
          Future.value(response)
      }
    }
  }

  private def jsonResponse(status: Status, body: JsValue): Future[Response] = {
    val response = Response(status)
    response.setContentString(body.toString())
    response.setContentTypeJson()
    Future.value(response)

  }

}

object Arrivals {
  implicit val arrivalsWrites: Writes[Arrivals] = Json.writes[Arrivals]
}

case class Arrivals(lineId: String)
