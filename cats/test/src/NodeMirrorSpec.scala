import org.scalatest.{GivenWhenThen, Outcome, fixture}

class NodeMirrorSpec extends fixture.FlatSpec with GivenWhenThen {
  behavior of "Node"

  it should "recognize mirrors of itself" in { f =>
    Given("an empty node")
    Then("should recognize itself as mirror")
    assert(f.emptyNode.isMirror(f.emptyNode))

    Given("an empty node and different value empty node")
    Then("should not recognize it as mirror")
    assert(!f.emptyNode.isMirror(f.otherEmptyNode))

    Given("non empty node and node that is mirror")
    Then("should recognize it as mirror")
    assert(f.nonEmptyNode.isMirror(f.nonEmptyMirrorNode))

    Given("non empty node and node that is mirror")
    Then("should recognize it as mirror")
    assert(f.nonEmptyNode.isMirror(f.nonEmptyMirrorNode))

    Given("non empty node 2 and node that is mirror")
    Then("should recognize it as mirror")
    assert(f.nonEmptyNode2.isMirror(f.nonEmptyMirrorNode2))


    Given("non empty node and node that is not mirror")
    Then("should not recognize it as mirror")
    assert(!f.nonEmptyNode.isMirror(f.nonEmptyNotMirrorNode))

    Given("non empty node and node that is not mirror")
    Then("should not recognize it as mirror")
    assert(!f.nonEmptyNode.isMirror(f.nonEmptyNotMirrorNode2))

    Given("non empty node and node that is not mirror")
    Then("should not recognize it as mirror")
    assert(!f.nonEmptyNode.isMirror(f.nonEmptyNotMirrorNode3))
  }

  override protected def withFixture(test: OneArgTest): Outcome = {
    test(new FixtureParam)
  }

  class FixtureParam {
    val emptyNode = Node(0, None, None)
    val otherEmptyNode = Node(1, None, None)
    val nonEmptyNode = Node(1, Some(Node(2, None, None)), Some(Node(3, None, None)))
    val nonEmptyNode2 = Node(1, Some(Node(2, None, Some(Node(5, None, None)))), Some(Node(3,Some(Node(4, None, None)), None)))
    val nonEmptyMirrorNode = Node(1, Some(Node(3, None, None)), Some(Node(2, None, None)))
    val nonEmptyMirrorNode2 = Node(1, Some(Node(3, None, Some(Node(4, None, None)))), Some(Node(2, Some(Node(5, None, None)), None)))
    val nonEmptyNotMirrorNode = Node(1, Some(Node(3, None, None)), None)
    val nonEmptyNotMirrorNode2 = Node(1, Some(Node(3, None, None)), None)
    val nonEmptyNotMirrorNode3 = Node(1, Some(Node(3, None, None)), None)
  }

}
