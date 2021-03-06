import scala.collection.mutable.{Map => MMap, Stack => MStack}
import scala.util.matching.Regex



case class State(vars: MMap[String, Double] = MMap.empty, exprStack: MStack[Expression] = MStack(), operatorStack: MStack[Operator] = MStack()) {
  def result: Expression = {
    if (operatorStack.isEmpty) {
      val res = exprStack.pop()
      res
    } else {
      operatorStack.pop() match {
        case AssignmentOperator =>
          val operand = exprStack.pop()
          val varExpression = exprStack.pop().asInstanceOf[VarExpression]
          AssignmentExpression(operand, varExpression.varname, varExpression.state)
        case UnaryOperator(op, _) =>
          val expr = exprStack.pop()
          exprStack.push(UnaryExpression(op, expr))
          result
        case BinaryOperator(op, _) =>
          val right = exprStack.pop()
          val left = exprStack.pop()
          exprStack.push(BinaryExpression(op, left, right))
          result
      }
    }
  }

  override def toString: String = {
    exprStack.toString() + "\n" + operatorStack.toString()
  }
}




