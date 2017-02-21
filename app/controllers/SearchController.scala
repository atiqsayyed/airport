package controllers

import javax.inject.{Inject, Singleton}

import model.{SearchResult, Runway, Airport, Country}
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, Controller}
import services.SearchService

import scala.concurrent.ExecutionContext.Implicits.global

@Singleton
class SearchController @Inject()(searchService: SearchService) extends Controller{

  def searchByCountry(country: String): Action[AnyContent] = Action.async {

    val resultF = searchService.searchCountriesByNameOrCountryCode(country)
    resultF.map(result => {
      Ok(views.html.search_results(result.toList))
    })
  }

  implicit val countryWritable = Json.writes[Country]
  implicit val airportWritable = Json.writes[Airport]
  implicit val runwayWritable = Json.writes[Runway]
  implicit val searchResultWritable = Json.writes[SearchResult]

}
