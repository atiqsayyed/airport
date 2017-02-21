package resolver

import javax.inject.{Inject, Singleton}

import client.MySqlClient
import model.{RunwayReport, SurfaceReport, CountryReport, SearchResult}

import scala.concurrent.Future

trait Resolver {
  def searchCountriesByNameOrCountryCode(countryCode: String): Future[Vector[SearchResult]]
  def findCountriesWithAirport(sortBy:String):Future[Vector[CountryReport]]
  def findCountryWithRunwaySurface: Future[Vector[SurfaceReport]]
  def findCommonRunwayIdents: Future[Vector[RunwayReport]]
}

@Singleton
class MySqlResolver @Inject() (mySqlClient: MySqlClient) extends Resolver{
  def searchCountriesByNameOrCountryCode(countryCode: String) = {
    mySqlClient.execute(SqlQueries.findCountriesByNameOrCode(countryCode))
  }

  def findCountriesWithAirport(sortBy: String): Future[Vector[CountryReport]] = {
    mySqlClient.execute(SqlQueries.findCountriesWithAirport(sortBy))
  }

  def findCountryWithRunwaySurface: Future[Vector[SurfaceReport]] = {
    mySqlClient.execute(SqlQueries.findCountryWithRunwaySurface)
  }

  def findCommonRunwayIdents: Future[Vector[RunwayReport]] ={
    mySqlClient.execute(SqlQueries.findCommonRunwayIdents)
  }
}
