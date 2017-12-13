import com.natpryce.hamkrest.*
import com.natpryce.hamkrest.assertion.assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import org.jetbrains.spek.api.dsl.on
import org.junit.Assert.assertEquals
import org.junit.platform.runner.JUnitPlatform
import org.junit.runner.RunWith

fun <T : Throwable> withMessage(stringMatcher: Matcher<String?>): Matcher<T> =
    has("message", { it.message }, stringMatcher)

@RunWith(JUnitPlatform::class)
class CalculatorSpec: Spek({
    given("a calculator") {
        val calculator = SampleCalculator()

        data class T(val i1: Int, val i2: Int, val expected: Int)
        val testData = listOf(
                T(2, 4, 6),
                T(0, 1, 1),
                T(10, -3, 7)
        )
        on("addition") {
            for ((i1, i2, expected) in testData) {
                val sum = calculator.sum(i1, i2)
                it("should give $expected for adding $i1 to $i2") {
                    assertEquals(expected, sum)
                }
            }
        }
        on("subtraction") {
            val subtract = calculator.subtract(4, 2)
            it("should return the result of subtracting the second number from the first number") {
                assertEquals(2, subtract)
            }
        }
        on("division by zero") {
            it("should throw on an attempt to divide by zero") {
                assert.that({
                    calculator.divide(3, 0)
                }, throws<ArithmeticException>(withMessage(present(containsSubstring("by zero")))))
            }
        }
    }
})

