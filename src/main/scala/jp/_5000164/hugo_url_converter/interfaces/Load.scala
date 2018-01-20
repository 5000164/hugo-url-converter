package jp._5000164.hugo_url_converter.interfaces

import java.io.File

object Load {
  def getTargetFilePathList(targetPath: String): List[String] = {
    new File(targetPath).listFiles.toList.flatMap {
      case d if d.isDirectory => getTargetFilePathList(d.getPath)
      case x => List(x.getPath)
    }
  }
}
