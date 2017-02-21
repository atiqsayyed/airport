package resolver

import javax.inject.{Inject, Singleton}

import client.MySqlClient
import model.{SearchResult, Country}

import scala.concurrent.Future

trait SearchResolver {

  def searchCountriesByNameOrCountryCode(countryCode: String): Future[Vector[SearchResult]]

}

@Singleton
class MySqlSearchResolver @Inject() (mySqlClient: MySqlClient) extends SearchResolver{
  def searchCountriesByNameOrCountryCode(countryCode: String) = {
    mySqlClient.execute(SearchQueries.findCountriesByNameOrCode(countryCode))
  }
}
