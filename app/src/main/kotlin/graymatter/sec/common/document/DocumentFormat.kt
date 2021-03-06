package graymatter.sec.common.document

import java.util.*

/**
 * An enum to indicate supported formats of documents this tool can process.
 */
enum class DocumentFormat(vararg validExtensions: String) {

    JSON("json") {
        override val alternateNames: Set<String> = setOf("json")
    },
    YAML("yaml", "yml") {
        override val alternateNames: Set<String> = setOf("yaml", "yml")
    },
    JAVA_PROPERTIES("properties") {
        override val alternateNames: Set<String> = setOf("properties", "props", "java_props", "java_properties")
    };

    val fileExtensions = validExtensions.map { it.lowercase(Locale.getDefault()) }.toList()
    val defaultFileExtension: String get() = fileExtensions.first()

    abstract val alternateNames: Set<String>

    companion object {

        @JvmStatic
        fun ofExt(ext: String): DocumentFormat {
            return values().first { ext.lowercase(Locale.getDefault()) in it.fileExtensions }
        }

        private val FORMATS_WITH_SUFFIXES: List<Pair<DocumentFormat, String>> =
            values().flatMap { format ->
                format.fileExtensions.map { ext ->
                    val suffix = ".${ext}".lowercase(Locale.getDefault())
                    format to suffix
                }
            }

        /**
         * Determine yhe document format
         */
        @JvmStatic
        fun ofFile(file: java.io.File): DocumentFormat? {
            return ofUri(file.name)
        }

        /**
         * Determines the format denoted by the last part of a file name after the DOT ("**`.`**") character.
         */
        @JvmStatic
        fun ofUri(name: String): DocumentFormat? {
            return FORMATS_WITH_SUFFIXES.firstOrNull { (_, suffix) -> name.endsWith(suffix, ignoreCase = true) }?.first
        }

        @JvmStatic
        fun named(namedFormat: String): DocumentFormat {
            val alternateName by lazy { namedFormat.lowercase(Locale.getDefault()).trim() }
            val found = values().firstOrNull { it.name == namedFormat || alternateName in it.alternateNames }
            return requireNotNull(found) { "Unable to find named format \"$namedFormat\"" }
        }

    }

}
