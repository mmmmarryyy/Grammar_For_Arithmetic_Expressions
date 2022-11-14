import kotlin.test.*
import java.io.File

internal class PartTests {

    @Test
    fun test1() {
        val file = File("input.txt")

        file.writeText("0;\n")

        assertEquals("0 = 0;\n", parse(file))
    }

    @Test
    fun test2() {
        val file = File("input.txt")

        file.writeText("1+2;\n")

        assertEquals("1+2 = 3;\n", parse(file))
    }

    @Test
    fun test2_2() {
        val file = File("input.txt")

        file.writeText("1 + 2;\n")

        assertEquals("1 + 2 = 3;\n", parse(file))
    }

    @Test
    fun test2_3() {
        val file = File("input.txt")

        file.writeText("1  +   2;\n")

        assertEquals("1  +   2 = 3;\n", parse(file))
    }

    @Test
    fun test3() {
        val file = File("input.txt")

        file.writeText("0;\n")
        file.appendText("1+2;\n")

        assertEquals("0 = 0;\n1+2 = 3;\n", parse(file))
    }

    @Test
    fun test4() {
        val file = File("input.txt")

        file.writeText("a = 5;\n")

        assertEquals("a = 5;\n", parse(file))
    }

    @Test
    fun test5() {
        val file = File("input.txt")

        file.writeText("a = 5;\n")
        file.appendText("a+2;\n")

        assertEquals("a = 5;\na+2 = 7;\n", parse(file))
    }
}

internal class ExampleTests {

    @Test
    fun test() {
        val file = File("input.txt")

        file.writeText("0;\n")
        file.appendText("1+2;\n")
        file.appendText("(1+2)*3;\n")
        file.appendText("a = 5;\n")
        file.appendText("a+2;\n")

        assertEquals("0 = 0;\n1+2 = 3;\n(1+2)*3 = 9;\na = 5;\na+2 = 7;\n", parse(file))
    }
}

internal class TestsForCorrectInput {

    @Test
    fun test1() {
        val file = File("input.txt")

        file.writeText("a = 5\n")
        file.appendText("a+2;\n")

        assertEquals("Line: 1; Message: missing ';' at '<EOF>'\na+2 = null;\n", parse(file))
    }
}