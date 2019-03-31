# Verspaetung

##Problem
In the fictional city of Verspaetung, public transport is notoriously unreliable. To tackle the problem, the city council has decided to make the public transport timetable and delay information public, opening up opportunities for innovative use cases.

You are given the task of writing a web API to expose the Verspaetung public transport information.

As a side note, the city of Verspaetung has been built on a strict grid - all location information can be assumed to be from a cartesian coordinate system.

##Data
The Verspaetung public transport information is comprised of 4 CSV files:

* lines.csv - the public transport lines.
* stops.csv - the stops along each line.
* times.csv - the time vehicles arrive & depart at each stop. The timetimestamp is in the format of HH:MM:SS.
* delays.csv - the delays for each line. This data is static and assumed to be valid for any time of day.

##Challenge
Build a web API which provides the following features:

* Find a vehicle for a given time and X & Y coordinates  
* Return the vehicle arriving next at a given stop 
* Indicate if a given line is currently delayed

Endpoints should be available via port 8081

## Use

**To Run** :
```sbt run main```

**To Test** :
```sbt test```

**Available endpoints:

```/delays/{lineName}```   
use example:  ```localhost:8081/delays/200``` 
   
```/arrivals/{stopNumber}```   
use example: ```localhost:8081/arrivals/0```    



##Assumptions and extensions

**General**
* There is only one of every vehicle, and each vehicle only has one route and timetable
* Architecture needs to be improved from one folder structure
* Data client currently untested
* Current time should come from request

**Delays:**   

* All Vehicles in existence are represented in the delay csv. 
* A Vehicle is delay if the delay is more than 1 (with the time assumed to be minutes) 
* This currently assumes early vehicles are also delayed.

**Next Arrivals**
* Doesn't take into account delays yet - only what is next due on the schedule
* Needs better error handling, currently only assumes nothing is due, not that stop may not exist
* Only one vehicle can arrive next
* Potentially extendable to take time given
* Requests should only allow Integers 

**Vehicle Location**
* Not built due to lack of time - but thoughts include:
    * Needs to take into account delays
    * mvp: only recognise vehicles that are at stops
    * Vehicles appear to move can only move 1 square at a time, both horizontally, vertically and diagonally. 