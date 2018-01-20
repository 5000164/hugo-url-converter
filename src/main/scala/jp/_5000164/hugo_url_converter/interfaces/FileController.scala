package jp._5000164.hugo_url_converter.interfaces

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Paths}

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

  def remove(targetPath: String): Unit = Files.delete(Paths.get(targetPath))

  def save(path: String, content: String): Unit = {
    val directoryPath = Paths.get(path.split('/').dropRight(1).mkString("/"))
    if (Files.notExists(directoryPath)) Files.createDirectories(directoryPath)
    Files.createFile(Paths.get(path))
    val pw = new PrintWriter(path)
    pw.write(content)
    pw.close()
  }
}
