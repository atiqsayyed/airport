package services

import javax.inject.{Inject, Singleton}

import resolver.Resolver

@Singleton
class SearchService @Inject()(searchResolver: Resolver) {
  def searchCountriesByNameOrCountryCode(countryCode: String) = {
    searchResolver.searchCountriesByNameOrCountryCode(countryCode)
  }
}
