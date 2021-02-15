package graymatter.sec.common.document

/**
 * An enum to indicate supported formats of documents this tool can process.
 */
enum class DocumentFormat(vararg validExtensions: String) {

    JSON("json"),
    YAML("yml", "yaml"),
    PROPERTIES("properties"),
    CSV("csv");

    val fileExtensions = validExtensions.map(String::toLowerCase).toList()
    val fileExtension: String get() = fileExtensions.first()

    companion object {

        private val FORMATS_WITH_SUFFIXES: List<Pair<DocumentFormat, String>> =
            values().flatMap { format ->
                format.fileExtensions.map { ext ->
                    val suffix = ".${ext}".toLowerCase()
                    format to suffix
                }
            }

        /**
         * Determine yhe document format
         */
        @JvmStatic
        fun ofFile(file: java.io.File): DocumentFormat? {
            return fromName(file.name)
        }

        /**
         * Determines the format denoted by the last part of a file name after the DOT ("**`.`**") character.
         */
        @JvmStatic
        fun fromName(name: String): DocumentFormat? {
            return FORMATS_WITH_SUFFIXES.firstOrNull { (_, suffix) -> name.endsWith(suffix, ignoreCase = true) }?.first
        }
    }

}