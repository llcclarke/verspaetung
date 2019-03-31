package main

import com.twitter.finagle.http.Request
import com.twitter.util.Await
import org.joda.time.LocalTime
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class ArrivalServiceSpec extends Specification with Mockito {

  trait Context extends Scope {

    val dataClient = mock[DataClient]
    val request = mock[Request]
    val arrivalService = new ArrivalService(dataClient)
    val currentTime = new LocalTime(18,30, 0)


  }

  "when stop has a vehicle due it returns 200 and line number" in new Context {

      dataClient.getArrivals("4", currentTime) returns  Some("200")
      val actualResult = arrivalService.getArrivals("4", currentTime)

      Await.result(actualResult.apply(request)).getContentString() ==== "{\"lineName\":\"200\"}"
      Await.result(actualResult.apply(request)).statusCode ==== 200

  }

    "when no stop time combination exists it should return 404" in new Context {

      dataClient.getArrivals("not a stop", currentTime)  returns None
      val actualResult = arrivalService.getArrivals("not a stop", currentTime)
      Await.result(actualResult.apply(request)).statusCode ==== 404
    }





}