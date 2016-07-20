package featuremodel

import java.util.UUID

import play.api.libs.json.{Format, JsPath, Json, Reads}
import play.api.libs.functional.syntax._

/**
  * @author Ole Meyer
  */

case class Feature(val id:String,val name:String, val active:Boolean, val constraints:Seq[Constraint]=Seq.empty[Constraint], val featureGroups: Seq[FeatureGroup]=Seq.empty[FeatureGroup]){}
case class FeatureGroup(val min:Int,val max:Int, val features:Seq[Feature])
case class SoftgoalInfluence(val softgoalId:String, val value:Double)
case class Softgoal(val id:String, val name:String)
case class FeatureModel(val id:String, val name:String, val rootFeature:Feature)
case class Constraint(constraintType:String,val featureId:String){
  val isRequires=constraintType.equals("requires")
  val isExcludes=constraintType.equals("excludes")
  if(!(isRequires|isExcludes))throw new UnknownConstraintTypeException
}



trait JsonReadsFeatureModel {

  implicit val constraintReads=(
    (JsPath\"constraintType").read[String] and
      (JsPath\"featureId").read[String]
    )(Constraint.apply _)

  implicit val featureReads:Reads[Feature]=(
    (JsPath\"id").read[String] and
      (JsPath\"name").read[String] and
      (JsPath\"active").read[Boolean] and
      (JsPath\"constraints").read[Seq[Constraint]] and
      (JsPath\"featureGroup").read[Seq[FeatureGroup]]
    )(Feature.apply _)

  implicit val featureGroupReads:Reads[FeatureGroup]=(
    (JsPath\"min").read[Int] and
      (JsPath\"max").read[Int] and
      (JsPath\"features").lazyRead[Seq[Feature]](Reads.seq[Feature](featureReads))
    )(FeatureGroup.apply _)

  implicit val softgoalInfluenceReads:Reads[SoftgoalInfluence]=(
    (JsPath\"softgoalId").read[String] and
      (JsPath\"value").read[Double]
    )(SoftgoalInfluence.apply _)

  implicit val softgoalReads:Reads[Softgoal]=(
    (JsPath\"id").read[String] and
      (JsPath\"name").read[String]
    )(Softgoal.apply _)

  implicit val featureModelRead:Reads[FeatureModel]=(
    (JsPath\"id").read[String] and
     (JsPath\"name").read[String] and
      (JsPath\"rootFeature").read[Feature]
    )(FeatureModel.apply _)

}

