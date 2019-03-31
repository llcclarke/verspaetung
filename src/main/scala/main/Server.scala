package main

import java.net.InetSocketAddress

import com.twitter.finagle.Http
import com.twitter.finagle.builder.ServerBuilder
import com.twitter.finagle.http.Request
import com.twitter.finagle.http.path.{Root, _}
import com.twitter.finagle.http.service.RoutingService
import com.twitter.util.Await
import org.joda.time.LocalTime

object Server extends App {

  val dataClient = new DataClient

  val delayService = new DelayService(dataClient)
  val arrivalService = new ArrivalService(dataClient)
  val currentTime = new LocalTime()



  val router = RoutingService.byPathObject[Request] {
    case Root / "delays" / line => delayService.getDelays(line)
    case Root / "arrivals" / stopId => arrivalService.getArrivals(stopId, currentTime)
  }

  val server = ServerBuilder()
    .stack(Http.server)
    .name("router")
    .bindTo(new InetSocketAddress(8081))
    .build(router)

  Await.ready(server)
}




