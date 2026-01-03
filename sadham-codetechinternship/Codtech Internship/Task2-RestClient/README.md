# Weather Client - REST API Task 2

A Java application that fetches real-time weather data from the wttr.in API and displays it in a formatted console output.

## Project Structure
```
Task2-RestClient/
├── src/WeatherClient.java
├── json.jar
└── README.md
```

## Requirements
- Java 11 or higher
- Internet connection

## How to Run

```bash
cd src
javac -cp ..\.json.jar -d ..\.out WeatherClient.java
java -cp "..\out;..\json.jar" WeatherClient
```

## Sample Output
```
===== WEATHER REPORT =====
Location     : Chennai
Temperature  : 28°C
Condition    : Mist
Humidity     : 74%
Wind Speed   : 19 km/h
=========================
```

## API
- **Endpoint**: https://wttr.in/Chennai?format=j1
- **Provider**: wttr.in
