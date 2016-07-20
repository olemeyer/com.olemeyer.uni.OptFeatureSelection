package fuzzy

/**
  * @author Ole Meyer
  */

abstract class MembershipFunction(parameterCount:Int, parameter:Seq[Double]){

  if(parameter.length<parameterCount)throw new NotEnoughParameterException

  val a:Double=if(parameterCount>0)parameter(0) else 0
  val b:Double=if(parameterCount>1)parameter(1) else 0
  val c:Double=if(parameterCount>2)parameter(2) else 0
  val d:Double=if(parameterCount>3)parameter(3) else 0

  def calc(x:Double):Double
}

/**
  * This is a triangle membership function.
  * @param parameter This sequence should contain three values. They determine the x coordinates of the corners.
  */
case class TriangleMembershipFunction(parameter:Seq[Double]) extends MembershipFunction(3,parameter) {
  override def calc(x: Double): Double = math.max(math.min((x-a)/(b-a),(c-x)/(c-b)),0)
}

/**
  * This is a trapezoid membership function.
  * @param parameter This sequence should contain four values. They determine the x coordinates of the corners.
  */
case class TrapezoidMembershipFunction(parameter:Seq[Double]) extends MembershipFunction(4,parameter){
  override def calc(x: Double): Double = math.max(math.min((x-a)/(b-a),math.min(1,(d-x)/(d-c))),0)
}

/**
  * This is a gaussian membership function
  * @param parameter This sequence should contain two values. The first one determines the center and the second on the width
  */
case class GaussianMembershipFunction(parameter:Seq[Double]) extends MembershipFunction(2,parameter){
  override def calc(x: Double): Double = math.pow(math.E,-(1/2)*math.pow((x-a)/b,2))
}

/**
  * This is a generalised bell membership function
  * @param parameter This sequence should contain three values. The first one determines the half width, the second one the slope
  *                  and the third one the center.
  */
case class BellMembershipFunction(parameter:Seq[Double]) extends MembershipFunction(3,parameter){
  override def calc(x: Double): Double = 1/(1+math.pow(math.abs((x-c)/a),2*b))
}

/**
  * This is a sigmoid membership function
  * @param parameter This sequence should contain two values. the first on determines the slope and the second one the crossover point
  */
case class SigmoidMembershipFunction(parameter:Seq[Double]) extends MembershipFunction(2,parameter){
  override def calc(x: Double): Double = 1/(1+math.exp(-a*(x-b)))
}
