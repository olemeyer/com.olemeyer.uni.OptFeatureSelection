package controllers

import featuremodel.{FeatureModel, JsonReadsFeatureModel}
import play.api.libs.json.JsError
import play.api.mvc._
import play.api.libs.json.Json._

object Application extends Controller with JsonReadsFeatureModel {

  def index=Action{
    Ok("This is the OptFeatureSelection API")
  }

  def setModel=Action(parse.json) { request =>
    request.body.validate[(FeatureModel)].map{
      case (featuremodel) => {
        Ok
      }
    }.recoverTotal{
      e => BadRequest("Detected error:"+ JsError.toFlatJson(e))
    }
  }
}
