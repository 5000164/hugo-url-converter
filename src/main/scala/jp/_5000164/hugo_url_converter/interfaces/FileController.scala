package jp._5000164.hugo_url_converter.interfaces

import java.io.File

import scala.io.Source

object FileController {
  def getTargetFilePathList(targetPath: String): List[String] = {
    new File(targetPath).listFiles.toList.flatMap {
      case d if d.isDirectory => getTargetFilePathList(d.getPath)
      case x => List(x.getPath)
    }.filter(_.takeRight(3) == ".md")
  }

  def getContent(filePath: String): List[String] = {
    Source.fromFile(filePath).getLines.toList
  }
}
