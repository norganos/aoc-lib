package de.linkel.aoc.utils.math.algebra

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test


class MatrixTests {
    private fun bd(vararg values: Int) = values.map { it.toBigDecimal() }.toList()

    @Test
    fun `matrix getAt works as expected`() {
        val m1 = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThat(m1.getAt(0, 0)).isEqualTo(1.toBigDecimal())
        Assertions.assertThat(m1.getAt(2, 0)).isEqualTo(3.toBigDecimal())
        Assertions.assertThat(m1.getAt(1, 1)).isEqualTo(5.toBigDecimal())
        Assertions.assertThat(m1.getAt(0, 2)).isEqualTo(7.toBigDecimal())
        Assertions.assertThat(m1.getAt(1, 2)).isEqualTo(8.toBigDecimal())
        val m2 = Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
        Assertions.assertThat(m2.getAt(0, 0)).isEqualTo(1.toBigDecimal())
        Assertions.assertThat(m2.getAt(2, 0)).isEqualTo(3.toBigDecimal())
        Assertions.assertThat(m2.getAt(1, 1)).isEqualTo(5.toBigDecimal())
    }
    @Test
    fun `matrix getAt fails when it should`() {
        val m = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThatThrownBy {
            m.getAt(3, 1)
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            m.getAt(1, 3)
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            m.getAt(-1, 1)
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            m.getAt(1, -1)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix getRow works as expected`() {
        val m = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThat(m.getRow(0))
            .isEqualTo(bd(1, 2, 3))
        Assertions.assertThat(m.getRow(1))
            .isEqualTo(bd(4, 5, 6))
        Assertions.assertThat(m.getRow(2))
            .isEqualTo(bd(7, 8, 9))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6)).getRow(1)
        ).isEqualTo(bd(3, 4))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)).getRow(1)
        ).isEqualTo(bd(4, 5, 6))
    }
    @Test
    fun `matrix getRow fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9)).getRow(3)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix getCol works as expected`() {
        val m = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThat(m.getCol(0))
            .isEqualTo(bd(1, 4, 7))
        Assertions.assertThat(m.getCol(1))
            .isEqualTo(bd(2, 5, 8))
        Assertions.assertThat(m.getCol(2))
            .isEqualTo(bd(3, 6, 9))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6)).getCol(1)
        ).isEqualTo(bd(2, 4, 6))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)).getCol(1)
        ).isEqualTo(bd(2, 5))
    }
    @Test
    fun `matrix getCol fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9)).getCol(3)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix removeRow works as expected`() {
        val m = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThat(m.removeRow(0))
            .isEqualTo(Matrix(3, 2, bd(4, 5, 6, 7, 8, 9)))
        Assertions.assertThat(m.removeRow(1))
            .isEqualTo(Matrix(3, 2, bd(1, 2, 3, 7, 8, 9)))
        Assertions.assertThat(m.removeRow(2))
            .isEqualTo(Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6)).removeRow(1)
        ).isEqualTo(Matrix(2, 2, bd(1, 2, 5, 6)))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)).removeRow(1)
        ).isEqualTo(Matrix(3, 1, bd(1, 2, 3)))
    }
    @Test
    fun `matrix removeRow fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9)).removeRow(3)
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(3, 1, bd(1, 2, 3)).removeRow(0)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix removeCol works as expected`() {
        val m = Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
        Assertions.assertThat(m.removeCol(0))
            .isEqualTo(Matrix(2, 3, bd(2, 3, 5, 6, 8, 9)))
        Assertions.assertThat(m.removeCol(1))
            .isEqualTo(Matrix(2, 3, bd(1, 3, 4, 6, 7, 9)))
        Assertions.assertThat(m.removeCol(2))
            .isEqualTo(Matrix(2, 3, bd(1, 2, 4, 5, 7, 8)))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6)).removeCol(1)
        ).isEqualTo(Matrix(1, 3, bd(1, 3, 5)))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)).removeCol(1)
        ).isEqualTo(Matrix(2, 2, bd(1, 3, 4, 6)))
    }
    @Test
    fun `matrix removeCol fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9)).removeCol(3)
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 3, bd(1, 2, 3)).removeCol(0)
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix appendCol works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .appendCol(bd(10, 11, 12))
        ).isEqualTo(Matrix(4, 3, bd(1, 2, 3, 10, 4, 5, 6, 11, 7, 8, 9, 12)))
        Assertions.assertThat(
            Matrix(2, 2, bd(1, 2, 3, 4))
                .appendCol(bd(10, 11))
        ).isEqualTo(Matrix(3, 2, bd(1, 2, 10, 3, 4, 11)))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
                .appendCol(bd(10, 11))
        ).isEqualTo(Matrix(4, 2, bd(1, 2, 3, 10, 4, 5, 6, 11)))
        Assertions.assertThat(
            Matrix(1, 1, bd(1))
                .appendCol(bd(2))
                .appendCol(bd(3))
        ).isEqualTo(Matrix(3, 1, bd(1, 2, 3)))
    }
    @Test
    fun `matrix appendCol fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).appendCol(bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).appendCol(bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).appendCol(bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).appendCol(bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix appendRow works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .appendRow(bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 4, bd(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)))
        Assertions.assertThat(
            Matrix(2, 2, bd(1, 2, 3, 4))
                .appendRow(bd(10, 11))
        ).isEqualTo(Matrix(2, 3, bd(1, 2, 3, 4, 10, 11)))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6))
                .appendRow(bd(10, 11))
        ).isEqualTo(Matrix(2, 4, bd(1, 2, 3, 4, 5, 6, 10, 11)))
        Assertions.assertThat(
            Matrix(1, 1, bd(1))
                .appendRow(bd(2))
                .appendRow(bd(3))
        ).isEqualTo(Matrix(1, 3, bd(1, 2, 3)))
    }
    @Test
    fun `matrix appendRow fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).appendRow(bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).appendRow(bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).appendRow(bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).appendRow(bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix insertRow works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertRow(0, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 4, bd(10, 11, 12, 1, 2, 3, 4, 5, 6, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertRow(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 4, bd(1, 2, 3, 10, 11, 12, 4, 5, 6, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertRow(2, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 4, bd(1, 2, 3, 4, 5, 6, 10, 11, 12, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertRow(3, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 4, bd(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12)))

        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6))
                .insertRow(1, bd(10, 11))
        ).isEqualTo(Matrix(2, 4, bd(1, 2, 10, 11, 3, 4, 5, 6)))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
                .insertRow(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 2, 3, 10, 11, 12, 4, 5, 6)))
    }
    @Test
    fun `matrix insertRow fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertRow(0, bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertRow(-1, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertRow(3, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertRow(0, bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).insertRow(0, bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).insertRow(0, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix replaceRow works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceRow(0, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(10, 11, 12, 4, 5, 6, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceRow(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 2, 3, 10, 11, 12, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceRow(2, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 10, 11, 12)))

        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6))
                .replaceRow(1, bd(10, 11))
        ).isEqualTo(Matrix(2, 3, bd(1, 2, 10, 11, 5, 6)))
        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
                .replaceRow(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 2, bd(1, 2, 3, 10, 11, 12)))
    }
    @Test
    fun `matrix replaceRow fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceRow(0, bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceRow(-1, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceRow(2, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceRow(0, bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).replaceRow(0, bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).replaceRow(0, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix insertCol works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertCol(0, bd(10, 11, 12))
        ).isEqualTo(Matrix(4, 3, bd(10, 1, 2, 3, 11, 4, 5, 6, 12, 7, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertCol(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(4, 3, bd(1, 10, 2, 3, 4, 11, 5, 6, 7, 12, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertCol(2, bd(10, 11, 12))
        ).isEqualTo(Matrix(4, 3, bd(1, 2, 10, 3, 4, 5, 11, 6, 7, 8, 12, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .insertCol(3, bd(10, 11, 12))
        ).isEqualTo(Matrix(4, 3, bd(1, 2, 3, 10, 4, 5, 6, 11, 7, 8, 9, 12)))

        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
                .insertCol(1, bd(10, 11))
        ).isEqualTo(Matrix(4, 2, bd(1, 10, 2, 3, 4, 11, 5, 6)))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6))
                .insertCol(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 10, 2, 3, 11, 4, 5, 12, 6)))
    }
    @Test
    fun `matrix insertCol fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertCol(0, bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertCol(-1, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertCol(3, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).insertCol(0, bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).insertCol(0, bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).insertCol(0, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix replaceCol works as expected`() {
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceCol(0, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(10, 2, 3, 11, 5, 6, 12, 8, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceCol(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 10, 3, 4, 11, 6, 7, 12, 9)))
        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9))
                .replaceCol(2, bd(10, 11, 12))
        ).isEqualTo(Matrix(3, 3, bd(1, 2, 10, 4, 5, 11, 7, 8, 12)))

        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
                .replaceCol(1, bd(10, 11))
        ).isEqualTo(Matrix(3, 2, bd(1, 10, 3, 4, 11, 6)))
        Assertions.assertThat(
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6))
                .replaceCol(1, bd(10, 11, 12))
        ).isEqualTo(Matrix(2, 3, bd(1, 10, 3, 11, 5, 12)))
    }
    @Test
    fun `matrix replaceCol fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceCol(0, bd(1))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceCol(-1, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceCol(2, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(2, 2, bd(1, 2, 3, 4)).replaceCol(0, bd(1, 2, 3))
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).replaceCol(0, bd())
        }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy {
            Matrix(1, 1, bd(1)).replaceCol(0, bd(1, 2))
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix det works as expected`() {
        Assertions.assertThat(
            Matrix(1, 1, bd(5)).det()
        ).isEqualTo(5.toBigDecimal())

        Assertions.assertThat(
            Matrix(2, 2, bd(1, 2, 3, 4)).det()
        ).isEqualTo((-2).toBigDecimal())

        Assertions.assertThat(
            Matrix(2, 2, bd(3, 7, 1, -4)).det()
        ).isEqualTo((-19).toBigDecimal())

        Assertions.assertThat(
            Matrix(2, 2, bd(1, 0, 0, 1)).det()
        ).isEqualTo(1.toBigDecimal())

        Assertions.assertThat(
            Matrix(3, 3, bd(-2, -1, 2, 2, 1, 4, -3, 3, -1)).det()
        ).isEqualTo(54.toBigDecimal())

        Assertions.assertThat(
            Matrix(3, 3, bd(1, 0, 0, 0, 1, 0, 0, 0, 1)).det()
        ).isEqualTo(1.toBigDecimal())
    }
    @Test
    fun `matrix det fails when it should`() {
        Assertions.assertThatThrownBy {
            Matrix(2, 3, bd(1, 2, 3, 4, 5, 6)).det()
        }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `matrix transpose works as expected`() {
        Assertions.assertThat(
            Matrix(1, 1, bd(5)).transpose()
        ).isEqualTo(
            Matrix(1, 1, bd(5))
        )

        Assertions.assertThat(
            Matrix(2, 2, bd(1, 2, 3, 4)).transpose()
        ).isEqualTo(
            Matrix(2, 2, bd(1, 3, 2, 4))
        )

        Assertions.assertThat(
            Matrix(3, 3, bd(1, 2, 3, 4, 5, 6, 7, 8, 9)).transpose()
        ).isEqualTo(
            Matrix(3, 3, bd(1, 4, 7, 2, 5, 8, 3, 6, 9))
        )

        Assertions.assertThat(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6)).transpose()
        ).isEqualTo(
            Matrix(2, 3, bd(1, 4, 2, 5, 3, 6))
        )

        Assertions.assertThat(
            Matrix(2, 3, bd(1, 4, 2, 5, 3, 6)).transpose()
        ).isEqualTo(
            Matrix(3, 2, bd(1, 2, 3, 4, 5, 6))
        )
    }
}