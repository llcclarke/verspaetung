package main

import org.joda.time.LocalTime
import org.specs2.mock.Mockito
import org.specs2.mutable.Specification
import org.specs2.specification.Scope

class DataClientSpec extends Specification with Mockito {

  trait Context extends Scope {

    val dataClient = new DataClient
    val currentTime = new LocalTime(9, 30, 0)


  }

  "Delays" >> {
    "Where vehicle number exits it should return some delay" in new Context {

      dataClient.getDelays("M4") ==== Some("1")

    }

    "Where vehicle number does not exist it should return None" in new Context {

      dataClient.getDelays("notALine") ==== None

    }
  }


  "Arrivals" >> {
    "Where vehicle arrives after current time at given stop it should return vehicle name" in new Context {

      dataClient.getArrivals("0", currentTime) ==== Some("M4")

    }

    "Where stop doesn't exist it should return None" in new Context {

      dataClient.getArrivals("notAStop", currentTime) ==== None

    }

    "Where arrival is after current time it should return None" in new Context {

      override val currentTime = new LocalTime(22, 30, 0)

      dataClient.getArrivals("0", currentTime) ==== None

    }
  }
}
