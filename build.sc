import mill._
import mill.define.Target
import mill.scalalib._
import mill.util.Loose

object sb extends ScalaModule {
  def scalaVersion = "2.12.8"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    ivy"org.jsoup:jsoup::1.8.3"
  )

  object test extends Tests {
    override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(ivy"org.scalatest::scalatest:3.0.5")
    def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}

object tb extends ScalaModule {
  def scalaVersion = "2.12.8"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    ivy"org.apache.logging.log4j::log4j-api-scala:11.0",
    ivy"org.apache.logging.log4j:log4j-api:2.11.0",
    ivy"org.apache.logging.log4j:log4j-core:2.11.0")

  override def mainClass = Some("Question3Transformations")

  object test extends Tests {
    override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
      ivy"org.scalatest::scalatest:3.0.5"
    )
    override def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}


object cats extends ScalaModule {
  def scalaVersion = "2.12.8"

  override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
    ivy"org.apache.logging.log4j::log4j-api-scala:11.0",
    ivy"org.apache.logging.log4j:log4j-api:2.11.0",
    ivy"org.apache.logging.log4j:log4j-core:2.11.0",
    ivy"org.typelevel::cats-core:1.6.0")

//  override def mainClass = Some("Question3Transformations")

  object test extends Tests {
    override def ivyDeps: Target[Loose.Agg[Dep]] = Agg(
      ivy"org.scalatest::scalatest:3.0.5"
    )
    override def testFrameworks = Seq("org.scalatest.tools.Framework")
  }
}
