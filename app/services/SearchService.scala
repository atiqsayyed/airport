package services

import javax.inject.{Inject, Singleton}

import resolver.SearchResolver

@Singleton
class SearchService @Inject()(searchResolver: SearchResolver) {
  def searchCountriesByNameOrCountryCode(countryCode: String) = {
    searchResolver.searchCountriesByNameOrCountryCode(countryCode)
  }
}
