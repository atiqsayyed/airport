package controllers

import javax.inject.{Inject, Singleton}

import play.api.mvc.{AnyContent, Action, Controller}
import services.ReportService
import scala.concurrent.ExecutionContext.Implicits.global
@Singleton
class ReportsController @Inject()(reportService: ReportService) extends Controller{

  def getCountriesWithHighestNoOfAirports: Action[AnyContent] = Action.async {
    val resultF = reportService.findCountriesWithHighestNoOfAirports
    resultF.map(result => {
      Ok(views.html.report("Countries with Highest Airports",result.toList))
    })
  }

  def getCountriesWithLowestNoOfAirports: Action[AnyContent] = Action.async {
    val resultF = reportService.findCountriesWithLowestNoOfAirports
    resultF.map(result => {
      Ok(views.html.report("Countries with Lowest Airports",result.toList))
    })
  }

  def getSurfacePerCountry: Action[AnyContent] = Action.async {
    val resultF = reportService.findSurfacePerCountry()
    resultF.map(result => {
      Ok(views.html.surface("Surface per country",result.toList))
    })
  }

  def getCommonRunwayIdents: Action[AnyContent] = Action.async {
    val resultF = reportService.findCommonRunwayIdents()
    resultF.map(result => {
      Ok(views.html.common_ident("top 10 Most Common Runway Identifications ",result.toList))
    })
  }
}
