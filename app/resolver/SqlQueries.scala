package resolver

import model._
import slick.driver.MySQLDriver.api._
import slick.jdbc.GetResult


object SqlQueries {
  implicit val searchResult = GetResult(r =>
    SearchResult(Country(r.<<[String], r.<<[String]), Airport(r.<<[String], r.<<[String]), Runway(r.<<[String], r.<<[Int])))

  implicit val countryReport = GetResult(r => CountryReport(Country(r.<<[String], r.<<[String]), r.<<[Int]))
  implicit val surfaceReport = GetResult(r => SurfaceReport(Country(r.<<[String], r.<<[String]), r.<<[String]))
  implicit val runwayReport = GetResult(r => RunwayReport(r.<<[String],r.<<[Int]))

  def findCountriesByNameOrCode(searchTerm: String) = {

    sql"""
       select DISTINCT COUNTRIES.NAME as CountryName, COUNTRIES.`CODE` as CountryCode, AIRPORT.`NAME` as AirportName,
       AIRPORT.`TYPE` as AirportType, RUNWAYS.`SURFACE` as RunwaySurface, RUNWAYS.`LIGHTED` as RunwayLighted from COUNTRIES
       join AIRPORT on AIRPORT.ISO_COUNTRY=COUNTRIES.CODE
       join RUNWAYS on RUNWAYS.`AIRPORT_IDENT`=AIRPORT.`IDENT`
       where COUNTRIES.name LIKE '#$searchTerm%' or COUNTRIES.code Like '#$searchTerm%';
  """.as[SearchResult]
  }

  def findCountriesWithAirport(sortBy: String) = {
    sql"""
         select COUNTRIES.NAME as CountryName,AIRPORT.ISO_COUNTRY as AirportCountry, COUNT(AIRPORT.ISO_COUNTRY) as count from AIRPORT
         JOIN COUNTRIES ON COUNTRIES.`CODE`=AIRPORT.`ISO_COUNTRY`
         GROUP BY AIRPORT.`ISO_COUNTRY`, COUNTRIES.NAME
         order by count #$sortBy, CountryName
         LIMIT 10;
    """.as[CountryReport]
  }

  def findCountryWithRunwaySurface = {
    sql"""
         select DISTINCT COUNTRIES.NAME as CountryName,COUNTRIES.`CODE` as CountryCode,  RUNWAYS.`SURFACE` as RunwaySurface from COUNTRIES
         join AIRPORT on AIRPORT.ISO_COUNTRY=COUNTRIES.CODE
         join RUNWAYS on RUNWAYS.`AIRPORT_IDENT`=AIRPORT.`IDENT`;
    """.as[SurfaceReport]
  }

  def findCommonRunwayIdents = {
    sql"""
         select RUNWAYS.`LE_IDENT` as RunwayIdent, COUNT(RUNWAYS.`LE_IDENT`) as count from RUNWAYS
         GROUP BY RUNWAYS.`LE_IDENT`
         ORDER BY count DESC
         limit 10;
    """.as[RunwayReport]
  }
}
