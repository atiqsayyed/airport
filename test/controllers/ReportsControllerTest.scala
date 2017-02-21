package controllers

import model._
import org.jsoup.Jsoup
import org.scalatest.concurrent.ScalaFutures
import org.scalatest.mock.MockitoSugar
import org.scalatest.{FunSpec, Matchers}
import play.api.test.FakeRequest
import services.ReportService
import org.mockito.Mockito._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class ReportsControllerTest extends FunSpec with Matchers with MockitoSugar with ScalaFutures{

  describe("Reports Controller"){
    it("should display country name and count of airports in country"){
      new Setup {
        val response = reportController.getCountriesWithHighestNoOfAirports(FakeRequest()).futureValue

        response.header.status should be(200)

        val expectedFirstRow = Jsoup.parse(views.html.report("Some Title",expectedSearchResult.toList).body).select("table > tbody > tr:nth-child(1) td").text()
        expectedFirstRow should be("Australia 100")
      }
    }
  }

  trait Setup{
    val mockReportService = mock[ReportService]
    val reportController = new ReportsController(mockReportService)
    val expectedSearchResult: Vector[CountryReport] = Vector(CountryReport(Country("Australia","AUS"),100))

    when(mockReportService.findCountriesWithHighestNoOfAirports).thenReturn(Future(expectedSearchResult))
  }

}
