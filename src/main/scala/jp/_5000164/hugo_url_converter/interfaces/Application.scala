package jp._5000164.hugo_url_converter.interfaces

import jp._5000164.hugo_url_converter.domain.Converter

/**
  * アプリケーション起動
  */
object Application extends App {
  val basePath = args(0)
  for (fromFilePath <- FileController.getTargetFilePathList(basePath)) {
    val fromContent = FileController.getContent(fromFilePath)
    val (toFilePath, toContent) = Converter.toEachDay(basePath, fromFilePath, fromContent)
    FileController.remove(fromFilePath)
    FileController.save(toFilePath, toContent)
  }
}
