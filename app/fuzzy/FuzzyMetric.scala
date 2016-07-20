package fuzzy

import play.api.libs.functional.syntax._
import play.api.libs.json.JsPath

/**
  * @author Ole Meyer
  */

case class FuzzyMetric(val id:String, val name:String, val objects:FuzzyObject)
case class FuzzyObject(val id:String, val name:String, val membershipFunction:MembershipFunction)
case class MembershipFunctionWrapper(val functionType:String, parameter:Seq[Double]) extends MembershipFunction(0,Seq.empty[Double]){

  val innerFunction:MembershipFunction=functionType match {
    case "triangle"=>TriangleMembershipFunction(parameter)
    case "trapezoid"=>TrapezoidMembershipFunction(parameter)
    case "gaussian"=>GaussianMembershipFunction(parameter)
    case "bell"=>BellMembershipFunction(parameter)
    case "sigmoid"=>SigmoidMembershipFunction(parameter)
    case ft=>throw new UnknowFunctionTypeException
  }

  override def calc(x: Double): Double = innerFunction.calc(x)
}

trait JsonReadsFuzzyMetric{

  implicit val membershipFunctionReads=(
    (JsPath\"functionType").read[String] and
      (JsPath\"parameter").read[Seq[Double]]
    )(MembershipFunctionWrapper.apply _)

  implicit val fuzzyObjectReads=(
    (JsPath\"id").read[String] and
      (JsPath\"name").read[String] and
      (JsPath\"membershipFunction").read[MembershipFunctionWrapper]
    )(FuzzyObject.apply _)

  implicit val fuzzyMetricReads=(
    (JsPath\"id").read[String] and
      (JsPath\"name").read[String] and
      (JsPath\"objects").read[FuzzyObject]
    )(FuzzyMetric.apply _)

}
