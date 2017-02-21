package resolver

import model.{Runway, Airport, SearchResult, Country}
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult


object SearchQueries {

  def findCountriesByNameOrCode(searchTerm:String) = {
  sql"""
       select DISTINCT COUNTRIES.NAME as CountryName, COUNTRIES.`CODE` as CountryCode, AIRPORT.`NAME` as AirportName,
       AIRPORT.`TYPE` as AirportType, RUNWAYS.`SURFACE` as RunwaySurface, RUNWAYS.`LIGHTED` as RunwayLighted from COUNTRIES
       join AIRPORT on AIRPORT.ISO_COUNTRY=COUNTRIES.CODE
       join RUNWAYS on RUNWAYS.`AIRPORT_IDENT`=AIRPORT.`IDENT`
       where COUNTRIES.name LIKE '#$searchTerm%' or COUNTRIES.code Like '#$searchTerm%';
  """.as[SearchResult]
  }

  implicit val searchResult = GetResult(r =>
    SearchResult(Country(r.<<[String], r.<<[String]),Airport(r.<<[String], r.<<[String]), Runway(r.<<[String], r.<<[Int])))



}
