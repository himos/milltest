import java.io.{BufferedInputStream, FileInputStream}
import java.util.zip.GZIPInputStream
import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}

import scala.io.Source

object TFIDF extends App {

  private def gis(s: String) = {
    new GZIPInputStream(new BufferedInputStream(new FileInputStream(s)))
  }

  private def getContents(filename: String) = {
    Source.fromInputStream(gis(filename)).getLines().mkString("\n")
  }

//  private val stripped = getContents("/Users/pavel/dev/milltest/sparkBeyond/resources/sample_sites/http___2sculpt.com.gz")
//  .replaceAll("<(.|\n)*?>", "")

  private val stripped = Jsoup
    .parse(getContents("/Users/pavel/dev/milltest/sparkBeyond/resources/sample_sites/http___2sculpt.com.gz"))
      .body().text()


  print(stripped)

//  private val splitted = stripped.replaceAll("^[.,\\s]+", "").split("[.,\\s]+")
//
//  private val grouped: Map[String, Array[String]] = splitted.groupBy(identity)
//
//  println(grouped)
//
//  val count: Map[String, Int] = grouped.mapValues(_.size)
//
//  println(count)
}