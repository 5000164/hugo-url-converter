package jp._5000164.hugo_url_converter.interfaces

/**
  * アプリケーション起動
  */
object Application extends App {
  val targetDirectory = args(0)
  val files = Load.getTargetFilePathList(targetDirectory)
  println(files)
}
