package lesson1

import java.io.File
import kotlin.test.assertEquals

abstract class AbstractFileTests {
    protected fun assertFileContent(name: String, expectedContent: String) {
        val file = File(name)
        val content = file.readLines().joinToString("\n")
        assertEquals(expectedContent.trim(), content.trim())
    }

    protected fun assertFile(name: String, name2: String) {
        val file = File(name)
        val file2 = File(name2)
        val content = file.readLines().joinToString("\n")
        val content2 = file2.readLines().joinToString("\n")
        assertEquals(content.trim(), content2.trim())
    }
}