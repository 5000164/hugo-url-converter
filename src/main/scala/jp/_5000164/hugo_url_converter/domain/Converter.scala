package jp._5000164.hugo_url_converter.domain

object Converter {
  def toEachDay(basePath: String, filePath: String, content: List[String]): (String, String) = {
    def calculate(basePath: String, filePath: String, content: String, remaining: List[String]): (String, String) = {
      def buildLineAndDetectFilePath(basePath: String, filePath: String, line: String, content: String): (String, String) = {
        def makeFileName(basePath: String, filePath: String, date: String): String = {
          val (year, month, day) = date.split('T').head.split('-') match {
            case Array(y, m, d) => (y, m.filterNot(_ == '0'), d.filterNot(_ == '0'))
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

    calculate(basePath, filePath, "", content)
  }
}
