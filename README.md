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

##Assumptions

**Delays:**   

* All Vehicles in existence are represented in the delay csv. 
* A Vehicle is delay if the delay is more than 1 (with the time assumed to be minutes) 
* This currently does not handle early Vehicles. 