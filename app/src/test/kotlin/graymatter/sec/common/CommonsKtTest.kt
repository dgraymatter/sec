package graymatter.sec.common

import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import kotlin.test.assertEquals

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class CommonsKtTest {

    @Nested
    inner class TrimToLine {

        private lateinit var givenTextBlock: String

        @Test
        fun testTrimToLine() {
            givenTextBlockOf(
                """
                1
                2
                3
                4
                J
                Q
                t
                """.trimToLine()
            )
            thenExpectLine("1234JQt")
        }

        @Test
        fun testTrimToLineWithIndent() {
            givenTextBlockOf(
                """
                1
                2
                    3
                    4
                5
                6
            """.trimIndentToLine()
            )
            thenExpectLine("12    3    456")
        }

        @Test
        fun testWithPrefix() {
            givenTextBlockOf("""|
                |1
                |2
                |3
                |4
                | 5
                | 6
                |
                |7
            """.trimMarginToLine())
            thenExpectLine("1234 5 67")
        }

        private fun givenTextBlockOf(s: String) {
            this.givenTextBlock = s
        }

        private fun thenExpectLine(expectedTextLine: String) {
            assertEquals(expectedTextLine, givenTextBlock)
        }
    }


}
