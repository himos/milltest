case class Node(v: Int, l:Option[Node], r: Option[Node]) {
  def isMirror(otherNode: Node): Boolean = {
//    Node.isMirrors(Some(this), Some(otherNode))
    Node.areMirrors(Some(this), Some(otherNode))
//    isMirror2(otherNode)
  }

  def isMirror2(otherNode: Node): Boolean = {
      v == otherNode.v && l.fold(otherNode.r.isEmpty)(tln => otherNode.r.fold(false)(orn => tln.isMirror(orn))) &&
        r.fold(otherNode.l.isEmpty)(trn => otherNode.l.fold(false)(oln => oln.isMirror(trn)))
  }

}

object Node {
  private def isMirrors(node1: Option[Node], node2: Option[Node]):Boolean = {
    (node1, node2) match {
      case (None, None) => true
      case (Some(_), None) => false
      case (None, Some(_)) => false
      case (Some(n1), Some(n2)) if n1.v != n2.v => false
      case (Some(n1), Some(n2)) => isMirrors(n1.r, n2.l) && isMirrors(n1.l, n2.r)
    }
  }

  def areMirrors(nOpt1: Option[Node], nOpt2: Option[Node]):Boolean = {
    nOpt1.fold(nOpt2.isEmpty)(n1 => nOpt2.fold(false)(n2 => n2.v == n1.v && areMirrors(n1.r, n2.l) && areMirrors(n1.l, n2.r)))
  }

}