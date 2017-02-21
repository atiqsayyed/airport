package services

import model.{Country, CountryReport, RunwayReport, SurfaceReport}
import org.scalatest.concurrent.{IntegrationPatience, ScalaFutures}
import org.scalatest.{FunSpec, Matchers}
import resolver.MySqlResolver
import utils.TestInjector

class ReportServiceIntegrationTest extends FunSpec with Matchers with TestInjector with ScalaFutures with IntegrationPatience {
  describe("Report Service") {
    val sqlResolver = getInjector.instanceOf[MySqlResolver]
    val service = new ReportService(sqlResolver)

    it("should generate report for top 10 Countries with highest no of airports") {
      val results = service.findCountriesWithHighestNoOfAirports.futureValue

      results.size should be(10)
      results.head should be(CountryReport(Country("United States", "US"), 14167))
    }

    it("should generate report for top 10 Countries with lowest no of airports") {
      val results = service.findCountriesWithLowestNoOfAirports.futureValue

      results.size should be(10)
      results.head should be(CountryReport(Country("Chad", "TD"), 1))
    }

    it("should generate report for surface per countries") {
      val results = service.findSurfacePerCountry().futureValue

      results.size should be(390)
      results.head should be(SurfaceReport(Country("Afghanistan", "AF"), "GVL"))
    }

    it("should generate report for top 10 runway idents") {
      val results = service.findCommonRunwayIdents().futureValue

      results.size should be(10)
      results.head should be(RunwayReport("H1", 5482))
    }
  }
}
