package de.linkel.aoc.utils.math.algebra

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class EquationTests {
    private fun bd(vararg values: Int) = values.map { it.toBigDecimal() }.toList()

    @Test
    fun `simple 2x2 cramer`() {
        Assertions.assertThat(
            Equations.cramer(
                Matrix(2, 2, bd(94, 22, 34, 67)),
                bd(8400, 5400)
            )
        ).isEqualTo(bd(80, 40))

        Assertions.assertThat(
            Equations.cramer(
                Matrix(2, 2, bd(26, 67, 66, 21)),
                bd(12748, 12176)
            )
        ).isNull()
    }

    @Test
    fun `simple 3x3 cramer`() {
        Assertions.assertThat(
            Equations.cramer(
                Matrix(3, 3, bd(2, 3, -1, 1, 1, 1, -3, -4, 3)),
                bd(5, 6, -5)
            )
        ).isEqualTo(bd(5, -1, 2))
    }
}