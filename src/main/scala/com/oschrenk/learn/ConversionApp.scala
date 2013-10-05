package com.oschrenk.learn

import java.io.File
import java.io.FileWriter
import java.io.PrintWriter
import scala.util.Random

object ConversionApp extends App{

  val LineDelimiter = "\n"
  val WordDelimiter = "\t"

  val xmlFile = new File(args.toList match {
    case Nil => throw new IllegalArgumentException("No path.")
    case x :: xs => x
  })
  println(s"Reading ${xmlFile.getAbsolutePath}")

  val xml = scala.xml.XML.loadFile(xmlFile)
  val path = xmlFile.getParentFile.getAbsolutePath
  println(path)

  val vocabularyByLecture =
    (xml \ "item")
    .filter(item => (item \ "@id").text.endsWith(".inv"))
    .map(item => ((item \ "cat").text, (item \ "Q").text,(item \ "A").text))
    .groupBy(_._1)
    .mapValues(
      itemList =>
        itemList.sortBy(_._1)
        .reverse
        .map(item =>
          (item._3, item._2))
    )
    .mapValues(
      itemList => Random.shuffle(itemList)
    )

  vocabularyByLecture.foreach(
    lecture =>
      withPrintWriter(path, s"${lecture._1}.csv") {
        writer =>
          lecture._2
            .foreach(item => writer.write(s"${item._1}$WordDelimiter${item._2}$LineDelimiter"))
      }
  )

  def withPrintWriter(dir:String, name:String)(f: (PrintWriter) => Any) {
    val file = new File(dir, name)
    val writer = new FileWriter(file)
    val printWriter = new PrintWriter(writer)
    try {
      f(printWriter)
    }
    finally {
      printWriter.close()
    }
  }
}
