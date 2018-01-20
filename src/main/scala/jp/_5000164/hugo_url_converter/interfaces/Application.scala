package jp._5000164.hugo_url_converter.interfaces

import jp._5000164.hugo_url_converter.domain.Converter

/**
  * アプリケーション起動
  */
object Application extends App {
  val basePath = args(0)
  val files = Load.getTargetFilePathList(basePath)
  for (filePath <- files) {
    val content = Load.getContent(filePath)
    val (toFilePath, toContent) = Converter.toEachDay(basePath, filePath, content)
  }
}
