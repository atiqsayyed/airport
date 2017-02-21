package controllers

import model.{Runway, Airport, Country, SearchResult}
import org.jsoup.Jsoup
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatest.{Matchers, FunSpec}
import play.api.test.FakeRequest
import services.SearchService
import org.mockito.Mockito._
import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global

class SearchControllerTest extends FunSpec with Matchers with MockitoSugar with ScalaFutures{

  describe("Search Controller"){

    it("should generate search results page for given search term"){
      new Setup {
        when(mockSearchService.searchCountriesByNameOrCountryCode("aus")).thenReturn(Future(expectedSearchResult))

        val response = searchController.searchByCountry("aus")(FakeRequest()).futureValue

        response.header.status should be(200)

        expectedFirstRow should be("Australia AUS Melbourne Airport small CONCRETE 1")
      }
    }
  }

  trait Setup{
    val mockSearchService = mock[SearchService]
    val searchController = new SearchController(mockSearchService)
    val expectedSearchResult: Vector[SearchResult] = Vector(SearchResult(Country("Australia","AUS"),Airport("Melbourne Airport","small"),Runway("CONCRETE",1)))
    val expectedFirstRow = Jsoup.parse(views.html.search_results(expectedSearchResult.toList).body).select("table > tbody > tr:nth-child(1) td").text()
  }

}
