package jp._5000164.hugo_url_converter.domain

object Converter {
  def toEachDay(basePath: String, filePath: String, content: List[String]): (String, String) = {
    def calculate(basePath: String, filePath: String, content: String, remaining: List[String]): (String, String) = {
      def buildLineAndDetectFilePath(basePath: String, filePath: String, line: String, content: String): (String, String) = {
        def makeFileName(basePath: String, filePath: String, date: String): String = {
          val (year, month, day) = date.split('T').head.split('-') match {
            case Array(y, m, d) => (
              y,
              if (m.head == '0') m.tail else m,
              if (d.head == '0') d.tail else d
            )
          }
          val fileName = filePath.split('/').last.replaceAll("_", "-")
          s"$basePath/$year/$month/$day/$fileName"
        }

        def makeAlias(url: String): String = s"""aliases = ["/$url/"]"""

        val dateRegex = """^date = (.*)$""".r
        val urlRegex = """^url = "(.*)"$""".r
        line match {
          case dateRegex(date) => (makeFileName(basePath, filePath, date), content + "\n" + line)
          case urlRegex(url) => (filePath, content + "\n" + makeAlias(url))
          case _ => (filePath, content + "\n" + line)
        }
      }

      remaining match {
        case line :: tail =>
          val (toFilePath, toContent) = buildLineAndDetectFilePath(basePath, filePath, line, content)
          calculate(basePath, toFilePath, toContent, tail)
        case Nil => (filePath, content)
      }
    }

    val (toFilePath, toContent) = calculate(basePath, filePath, "", content)

    // 最初に空文字を渡しているので先頭に必ず改行が入るので改行を削除する
    val removedContent = toContent.tail

    // ファイルの最後の改行の数を揃える
    val formattedContent =
      if (removedContent.takeRight(2) == "\n\n") removedContent
      else removedContent + "\n"

    (toFilePath, formattedContent)
  }
}
