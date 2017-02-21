package controllers

import javax.inject.Singleton

import play.api.mvc.{Action, Controller}

@Singleton
class HomeController extends Controller{

  def index()=Action{
    Ok(views.html.index())
  }

}
