import org.antlr.v4.runtime.*
import java.io.File

class Visitor: arithmetic_expressionsVisitor<Int?>, arithmetic_expressionsBaseVisitor<Int?>() {
    val memory: MutableMap<String, Int?> = mutableMapOf()

    override fun visitStatement(ctx: arithmetic_expressionsParser.StatementContext): Int? =
        if (ctx.variable() != null) visitVariable(ctx.variable())
            else if (ctx.expression() != null) visitExpression(ctx.expression())
                else null

    override fun visitExpression(ctx: arithmetic_expressionsParser.ExpressionContext): Int? {
        val subtree = ctx.children ?: return null
        val expressions = ctx.expression()

        return if (subtree.size == 3) {
            val firstExpression = visitExpression(expressions[0])
            if (subtree[0].toString() == "(") {
                firstExpression
            } else {
                val secondExpression = visitExpression(expressions[1])

                if (firstExpression == null || secondExpression == null) null
                    else if (subtree[1].toString() == "*") firstExpression * secondExpression
                        else firstExpression + secondExpression
            }
        } else {
            if (ctx.INT() != null) ctx.INT().toString().toIntOrNull()
                else if (ctx.VARIABLE() != null) memory[ctx.VARIABLE().toString()]
                    else null
        }
    }

    override fun visitVariable(ctx: arithmetic_expressionsParser.VariableContext): Int? {
        if (ctx.VARIABLE() != null) memory[ctx.VARIABLE().toString()] = visitExpression(ctx.expression())
        return visitExpression(ctx.expression())
    }
}

class Errors: BaseErrorListener() {
    var error: String = ""

    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String,
        e: RecognitionException?
    ) {
        error = "Line: $line; Message: $msg\n"
    }
}

fun parse(inputFile: File): String {
    val visitor = Visitor()
    var result = ""

    inputFile.forEachLine {
        val parser = arithmetic_expressionsParser(
            CommonTokenStream(arithmetic_expressionsLexer(CharStreams.fromString(it)))
        )

        val errorListener = Errors()

        parser.removeErrorListeners()
        parser.addErrorListener(errorListener)
        parser.statement()

        result += if (errorListener.error != "") {
            errorListener.error
        } else {
            parser.reset()
            it.dropLast(1).split('=')[0].trim() + " = " + (visitor.visitStatement(
                parser.statement()
            )?.toString() ?: "null") + ";\n"
        }
    }
    return result
}

fun main() {
    print(parse(File("input.txt")))
}