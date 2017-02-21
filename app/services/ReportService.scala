package services

import javax.inject.{Inject, Singleton}

import model.CountryReport
import resolver.Resolver

import scala.concurrent.Future

@Singleton
class ReportService @Inject()(reportResolver: Resolver) {
  def findCountriesWithHighestNoOfAirports: Future[Vector[CountryReport]] = findCountriesWithAirport("DESC")
  def findCountriesWithLowestNoOfAirports: Future[Vector[CountryReport]] = findCountriesWithAirport("ASC")

  private def findCountriesWithAirport(sortBy:String) = {
    reportResolver.findCountriesWithAirport(sortBy)
  }

  def findSurfacePerCountry() = {
    reportResolver.findCountryWithRunwaySurface
  }

  def findCommonRunwayIdents() = {
    reportResolver.findCommonRunwayIdents
  }
}
