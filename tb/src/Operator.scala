trait Operator{
  def precedence: Int
}

object Operator {
  def getUni(op: String): Operator  = {
    op match {
      case "+" => UnaryOperator(x => x, 100)
      case "-" => UnaryOperator(x => -x, 100)
      case x   => throw new IllegalArgumentException(s"Expecting unary operator, but got $x")
    }
  }

  def getBi(op: String): Operator  = {
    op match {
      case "+" => BinaryOperator((x, y) => x + y, 80)
      case "-" => BinaryOperator((x, y) => x - y, 80)
      case "*" => BinaryOperator((x, y) => x * y, 90)
      case "/" => BinaryOperator((x, y) => x / y, 90)
    }
  }
}

case class UnaryOperator(op: Double => Double, override val precedence: Int) extends Operator
case class BinaryOperator(op: (Double, Double) => Double, override val precedence: Int) extends Operator
case object AssignmentOperator extends Operator{
  override val precedence: Int = 10
}

abstract class Bracket extends Operator {
  override val precedence: Int = 120
}
case object OpenBracket extends Bracket
case object CloseBracket extends Bracket
