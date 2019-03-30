package main

import com.twitter.finagle.Service
import com.twitter.finagle.http.{Request, Response, Status}
import com.twitter.util.Future
import play.api.libs.json._


class DelayService(dataClient: DataClient) {

  def getDelays(routeName: String) = new Service[Request, Response] {

    def apply(req: Request): Future[Response] = {
      dataClient.getDelays(routeName) match {
        case Some("0")  =>jsonResponse(Status.Ok, Json.toJson(Delays(false)))
        case Some(delayInfo) =>
          jsonResponse(Status.Ok, Json.toJson(Delays(true)))
        case None =>
          val response = Response(Status.NotFound)
          response.setContentString("Error: Line not found")
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

object Delays {
  implicit val delaysWrites: Writes[Delays] = Json.writes[Delays]
}

case class Delays(delay: Boolean)






