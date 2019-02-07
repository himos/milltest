import org.apache.logging.log4j.scala.Logging

trait Expression {
  def result: Double
}


case class BinaryExpression(operator: (Double, Double) => Double, left: Expression, right: Expression) extends Expression {
  override def result: Double = operator(left.result, right.result)
}

case class UnaryExpression(operator: Double => Double, expr: Expression) extends Expression {
  override def result: Double = operator(expr.result)
}

case class VarExpression(varname: String, state: State) extends Expression {
  override def result: Double = state.vars(varname)
}

case class AssignmentExpression(op: Expression, varname: String, state: State) extends Expression {
  override def result: Double = {
    state.vars(varname) = op.result
    state.vars(varname)
  }
}

case class SimpleExpression(result: Double) extends Expression

object Expression extends Logging {
  def apply(tokenizer: Tokenizer, state: State): Expression = {
    logger.info(s"Received new token stream: $tokenizer")
    var lastProcessedToken: Token = null

    while (tokenizer.hasNext) {
      val currentToken = tokenizer.next()
      logger.debug(s"Processing token: $currentToken")
      currentToken match {
        case NumberToken(num) =>
          state.exprStack.push(SimpleExpression(num))

        case OperatorToken(opStr) =>
          val op = if (state.exprStack.nonEmpty && !lastProcessedToken.isInstanceOf[OperatorToken])
            Operator.getBi(opStr)
           else
            Operator.getUni(opStr)
          pushOp(op, state)

        case OpenBracketToken =>
          state.operatorStack.push(OpenBracket)

        case CloseBracketToken =>
          while(state.operatorStack.head != OpenBracket) popOp(state)
          state.operatorStack.pop()

        case VariableToken(varname) =>
          state.exprStack.push(VarExpression(varname, state))

        case AssignmentToken =>
          if(state.exprStack.head.isInstanceOf[VarExpression]){
            state.operatorStack.push(AssignmentOperator)
          }

      }

      lastProcessedToken = currentToken
    }
    logger.trace(state.vars)
    logger.trace(state.exprStack.map(_.getClass))
    logger.trace(state.operatorStack)
    state.result
  }

 private[this] def pushOp(op: Operator, state: State): Unit = {
    val opStack = state.operatorStack
    while (opStack.nonEmpty && opStack.head != OpenBracket && opStack.head.precedence > op.precedence) {
      popOp(state)
    }
    opStack.push(op)
  }


  private[this] def popOp(state: State): Unit = {
    val exprStack = state.exprStack
    state.operatorStack.pop() match {
      case AssignmentOperator =>
        val operand = exprStack.pop()
        val varExpression = exprStack.pop().asInstanceOf[VarExpression]
        exprStack.push(AssignmentExpression(operand, varExpression.varname, varExpression.state))
      case UnaryOperator(op, _) =>
        val expr = exprStack.pop()
        exprStack.push(UnaryExpression(op, expr))
      case BinaryOperator(op, _) =>
        val right = exprStack.pop()
        val left = exprStack.pop()
        exprStack.push(BinaryExpression(op, left, right))
    }
  }

}
