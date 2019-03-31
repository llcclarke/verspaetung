package main

import com.twitter.finagle.http.Request
import com.twitter.util.Await
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope


class DelayServiceSpec extends Specification with Mockito {

  trait Context extends Scope {

    val dataClient = mock[DataClient]
    val request = mock[Request]
    val delayService = new DelayService(dataClient)

  }

  "when line has a delay should return a 200 status code and content string indicates a delay" in new Context {

    dataClient.getDelays("200") returns Some("2")
    val actualResult = delayService.getDelays("200")


    Await.result(actualResult.apply(request)).getContentString() ==== "{\"delay\":true}"
    assert(Await.result(actualResult.apply(request)).statusCode ==== 200)
  }

  "when line has no delay should return a 200 status code and content string indicates no delay" in new Context {

    dataClient.getDelays("201") returns Some("0")
    val actualResult = delayService.getDelays("201")

    Await.result(actualResult.apply(request)).getContentString() ==== "{\"delay\":false}"
    Await.result(actualResult.apply(request)).statusCode ==== 200
  }

  "when line is not available should return a 404 status code" in new Context {

    dataClient.getDelays("notALine") returns None
    val actualResult = delayService.getDelays("notALine")

    Await.result(actualResult.apply(request)).statusCode ==== 404
  }
}
