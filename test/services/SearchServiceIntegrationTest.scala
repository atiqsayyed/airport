package services

import model.{Airport, Country, Runway, SearchResult}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{FunSpec, Matchers}
import resolver.MySqlResolver
import utils.TestInjector

class SearchServiceIntegrationTest extends FunSpec with Matchers with TestInjector with ScalaFutures with IntegrationPatience {

  describe("Search Service") {
    val searchResolver = getInjector.instanceOf[MySqlResolver]
    val service = new SearchService(searchResolver)

    it("should get search results if searched by country name") {
      val expectedSearchResult= SearchResult(Country("Australia", "AU"), Airport("Allambie Airport", "small_airport"),Runway("X", 0))
      val searchResults = service.searchCountriesByNameOrCountryCode("australia").futureValue

      searchResults.length should be(799)
      searchResults.count(_.country.name == "Australia") should be(799)
      searchResults.head should be(expectedSearchResult)
    }

    it("should get search results if searched by country code") {
      val expectedSearchResult= SearchResult(Country("Australia", "AU"), Airport("Allambie Airport", "small_airport"),Runway("X", 0))
      val searchResults = service.searchCountriesByNameOrCountryCode("au").futureValue

      searchResults.length should be(799)
      searchResults.count(_.country.name == "Australia") should be(799)
      searchResults.head should be(expectedSearchResult)
    }

    it("should get search results if searched for fuzzy term") {
      val expectedSearchResult= SearchResult(Country("Australia", "AU"), Airport("Allambie Airport", "small_airport"),Runway("X", 0))
      val searchResults = service.searchCountriesByNameOrCountryCode("aus").futureValue

      searchResults.length should be(799)
      searchResults.count(_.country.name == "Australia") should be(799)
      searchResults.head should be(expectedSearchResult)
    }

  }

}
