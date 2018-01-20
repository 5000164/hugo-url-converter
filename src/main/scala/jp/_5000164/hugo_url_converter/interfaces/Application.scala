package jp._5000164.hugo_url_converter.interfaces

import jp._5000164.hugo_url_converter.domain.Converter

/**
  * アプリケーション起動
  */
object Application extends App {
  val basePath = args(0)
  val files = FileController.getTargetFilePathList(basePath)
  for (filePath <- files) {
    val content = FileController.getContent(filePath)
    val (toFilePath, toContent) = Converter.toEachDay(basePath, filePath, content)
    FileController.remove(filePath)
    FileController.save(toFilePath, toContent)
  }
}
