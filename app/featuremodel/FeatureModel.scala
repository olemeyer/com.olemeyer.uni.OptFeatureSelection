package featuremodel

import java.util.UUID

import play.api.libs.json.{Format, JsPath, Json, Reads}
import play.api.libs.functional.syntax._

/**
  * @author Ole Meyer
  */

case class Feature(val id:String,val active:Option[Boolean], val featureGroups: Option[Seq[FeatureGroup]])
case class FeatureGroup(val min:Int,val max:Int, val features:Seq[Feature])
case class SoftgoalInfluence(val softgoalId:String, val value:Double)
case class Softgoal(val id:String)
case class FeatureModel(val id:String, val rootFeature:Feature, val softgoals:Seq[Softgoal], val constraints:Option[Seq[Constraint]])
case class Constraint(constraintTypeString:String,val fromId:String, val toId:String){
  val constraintType=if (constraintTypeString.equals("requires")) ConstraintType.Requires else ConstraintType.Excludes


}

object ConstraintType extends Enumeration{
  type ConstraintType=Value
  val Requires=Value("requires")
  val Excludes=Value("excludes")
}



trait JsonReadsFeatureModel {

  implicit val constraintReads=Json.reads[Constraint]
  implicit val softgoalReads=Json.reads[Softgoal]

  implicit val featureReads:Reads[Feature]=(
    (JsPath\"id").read[String] and
      (JsPath\"active").readNullable[Boolean] and
      (JsPath\"featureGroups").lazyReadNullable[Seq[FeatureGroup]](Reads.seq[FeatureGroup](featureGroupReads))
    )(Feature.apply _)

  implicit val featureGroupReads:Reads[FeatureGroup]=(
    (JsPath\"min").read[Int] and
      (JsPath\"max").read[Int] and
      (JsPath\"features").lazyRead[Seq[Feature]](Reads.seq[Feature](featureReads))
    )(FeatureGroup.apply _)

  implicit val softgoalInfluenceReads=Json.reads[SoftgoalInfluence]

  implicit val featureModelReads=Json.reads[FeatureModel]


}

