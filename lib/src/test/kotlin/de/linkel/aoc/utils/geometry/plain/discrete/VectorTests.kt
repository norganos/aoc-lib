package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.VectorD
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VectorTests {

    @Test
    fun `test Vector constructor`() {
        val point = Vector(2, 3)
        Assertions.assertThat(point.deltaX).isEqualTo(2)
        Assertions.assertThat(point.deltaY).isEqualTo(3)
    }

    @Test
    fun `test Vector equals method`() {
        val vector1 = Vector(2, 3)
        val vector2 = Vector(2, 3)
        val vector3 = Vector(3, 4)
        Assertions.assertThat(vector1).isEqualTo(vector2)
        Assertions.assertThat(vector1).isNotEqualTo(vector3)
    }
    
    @Test
    fun `test Vector distance calculation`() {
        Assertions.assertThat(Vector(3, 4).distance).isEqualTo(5.0)
    }

    @Test
    fun `test Vector manhattanDistance calculation`() {
        Assertions.assertThat(Vector(3, 4).manhattanDistance).isEqualTo(7)
    }

    @Test
    fun `test Vector maximumAxisDistance calculation`() {
        Assertions.assertThat(Vector(3, 4).maximumAxisDistance).isEqualTo(4)
    }

    @Test
    fun `test Vector isVertical calculation`() {
        Assertions.assertThat(Vector(3, 4).isVertical).isFalse()
        Assertions.assertThat(Vector(0, 4).isVertical).isTrue()
    }

    @Test
    fun `test Vector isHorizontal calculation`() {
        Assertions.assertThat(Vector(3, 4).isHorizontal).isFalse()
        Assertions.assertThat(Vector(3, 0).isHorizontal).isTrue()
    }

    @Test
    fun `test Vector plus method`() {
        val vector1 = Vector(1, 2)
        Assertions.assertThat(vector1 + Vector.NORTH).isEqualTo(Vector(1, 1))
        Assertions.assertThat(vector1 + Vector.SOUTH).isEqualTo(Vector(1, 3))
        Assertions.assertThat(vector1 + Vector.WEST).isEqualTo(Vector(0, 2))
        Assertions.assertThat(vector1 + Vector.EAST).isEqualTo(Vector(2, 2))
        Assertions.assertThat(vector1 + Vector.NORTH_WEST).isEqualTo(Vector(0, 1))
        Assertions.assertThat(vector1 + Vector.NORTH_EAST).isEqualTo(Vector(2, 1))
        Assertions.assertThat(vector1 + Vector.SOUTH_WEST).isEqualTo(Vector(0, 3))
        Assertions.assertThat(vector1 + Vector.SOUTH_EAST).isEqualTo(Vector(2, 3))

        Assertions.assertThat(Vector.ZERO + Vector.NORTH).isEqualTo(Vector(0, -1))
        Assertions.assertThat(Vector.ZERO + Vector.WEST).isEqualTo(Vector(-1, 0))

        Assertions.assertThat(Vector.NORTH + Vector.WEST).isEqualTo(Vector.NORTH_WEST)
        Assertions.assertThat(Vector.SOUTH_WEST + Vector.EAST).isEqualTo(Vector.SOUTH)
    }

    @Test
    fun `test Vector minus method`() {
        val vector1 = Vector(1, 2)
        Assertions.assertThat(vector1 - Vector.NORTH).isEqualTo(Vector(1, 3))
        Assertions.assertThat(vector1 - Vector.SOUTH).isEqualTo(Vector(1, 1))
        Assertions.assertThat(vector1 - Vector.WEST).isEqualTo(Vector(2, 2))
        Assertions.assertThat(vector1 - Vector.EAST).isEqualTo(Vector(0, 2))
        Assertions.assertThat(vector1 - Vector.NORTH_WEST).isEqualTo(Vector(2, 3))
        Assertions.assertThat(vector1 - Vector.NORTH_EAST).isEqualTo(Vector(0, 3))
        Assertions.assertThat(vector1 - Vector.SOUTH_WEST).isEqualTo(Vector(2, 1))
        Assertions.assertThat(vector1 - Vector.SOUTH_EAST).isEqualTo(Vector(0, 1))

        Assertions.assertThat(Vector.ZERO - Vector.NORTH).isEqualTo(Vector(0, 1))
        Assertions.assertThat(Vector.ZERO - Vector.WEST).isEqualTo(Vector(1, 0))
    }

    @Test
    fun `test Vector times method`() {
        Assertions.assertThat(Vector(3,1) * 3).isEqualTo(Vector(9, 3))
        Assertions.assertThat(Vector(3,-1) * 2).isEqualTo(Vector(6, -2))
        Assertions.assertThat(Vector(-3,1) * 2).isEqualTo(Vector(-6, 2))
        Assertions.assertThat(Vector.ZERO * 2).isEqualTo(Vector.ZERO)
    }

    @Test
    fun `test Vector turnClockwise method`() {
        Assertions.assertThat(Vector(3,1).turnClockwise()).isEqualTo(Vector(-1, 3))
        Assertions.assertThat(Vector(-1, -1).turnClockwise()).isEqualTo(Vector(1, -1))
        val vector1 = Vector(3,1)
        Assertions.assertThat(vector1.turnClockwise().turnClockwise().turnClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnClockwise().turnCounterClockwise()).isEqualTo(vector1)
        Assertions.assertThat(Vector.ZERO.turnClockwise()).isEqualTo(Vector.ZERO)
    }

    @Test
    fun `test Vector turnCounterClockwise method`() {
        Assertions.assertThat(Vector(3,1).turnCounterClockwise()).isEqualTo(Vector(1, -3))
        Assertions.assertThat(Vector(-1, -1).turnCounterClockwise()).isEqualTo(Vector(-1, 1))
        val vector1 = Vector(3,1)
        Assertions.assertThat(vector1.turnCounterClockwise().turnCounterClockwise().turnCounterClockwise().turnCounterClockwise()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnCounterClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(Vector.ZERO.turnCounterClockwise()).isEqualTo(Vector.ZERO)
    }

    @Test
    fun `test Vector turnAround method`() {
        Assertions.assertThat(Vector(3,1).turnAround()).isEqualTo(Vector(-3, -1))
        Assertions.assertThat(-Vector(3,1)).isEqualTo(Vector(-3, -1))
        val vector1 = Vector(3,1)
        Assertions.assertThat(vector1.turnAround().turnAround()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnAround().turnClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(Vector(-1, -1).turnAround()).isEqualTo(Vector(1, 1))
        Assertions.assertThat(-Vector(-1, -1)).isEqualTo(Vector(1, 1))
        Assertions.assertThat(Vector.ZERO.turnAround()).isEqualTo(Vector.ZERO)
        Assertions.assertThat(-Vector.ZERO).isEqualTo(Vector.ZERO)
    }

    @Test
    fun `test Vector div method`() {
        Assertions.assertThat(Vector(9,3) / 3).isEqualTo(Vector(3, 1))
        Assertions.assertThat(Vector(6,4) / 2).isEqualTo(Vector(3, 2))
        Assertions.assertThatThrownBy { Vector(9,3) / 0 }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { Vector(6,4) / 3 }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test Vector contains method`() {
        Assertions.assertThat(Vector(3, 1) in Vector(9,3)).isTrue()
        Assertions.assertThat(Vector(6, 2) in Vector(9,3)).isTrue()
        Assertions.assertThat(Vector(9, 3) in Vector(9,3)).isTrue()
        Assertions.assertThat(Vector(3, -1) in Vector(9,3)).isFalse()
        Assertions.assertThat(Vector(-3, 1) in Vector(9,3)).isFalse()
        Assertions.assertThat(Vector(2, 1) in Vector(9,3)).isFalse()
        Assertions.assertThat(Vector(-3, -1) in Vector(9,3)).isFalse()
        Assertions.assertThat(Vector(9, 3) in Vector(6,2)).isFalse()
        Assertions.assertThat(Vector.ZERO in Vector(6,2)).isFalse()
        Assertions.assertThat(Vector.NORTH in Vector.ZERO).isFalse()
        Assertions.assertThat(Vector.ZERO in Vector.ZERO).isTrue()
    }

    @Test
    fun `test Vector parallel method`() {
        Assertions.assertThat(Vector(9,3) parallel Vector(3, 1)).isTrue()
        Assertions.assertThat(Vector(3,1) parallel Vector(6, 2)).isTrue()
        Assertions.assertThat(Vector(3,1) parallel Vector(-6, -2)).isTrue()
        Assertions.assertThat(Vector(9,3) parallel Vector(3, 2)).isFalse()
        Assertions.assertThat(Vector(9,3) parallel Vector.ZERO).isFalse()
        Assertions.assertThat(Vector.ZERO parallel Vector(9,3)).isFalse()
        Assertions.assertThat(Vector.ZERO parallel Vector.ZERO).isTrue()
    }

    @Test
    fun `test Vector notParallel method`() {
        Assertions.assertThat(Vector(9,3) notParallel Vector(3, 1)).isFalse()
        Assertions.assertThat(Vector(3,1) notParallel Vector(6, 2)).isFalse()
        Assertions.assertThat(Vector(-3,-1) notParallel Vector(6, 2)).isFalse()
        Assertions.assertThat(Vector(9,3) notParallel Vector(3, 2)).isTrue()
        Assertions.assertThat(Vector(9,3) notParallel Vector.ZERO).isTrue()
        Assertions.assertThat(Vector.ZERO notParallel Vector(9,3)).isTrue()
        Assertions.assertThat(Vector.ZERO notParallel Vector.ZERO).isFalse()
    }

    @Test
    fun `test Vector orthogonal method`() {
        Assertions.assertThat(Vector(9,3) orthogonal Vector(-1, 3)).isTrue()
        Assertions.assertThat(Vector(3,1) orthogonal Vector(2, -6)).isTrue()
        Assertions.assertThat(Vector(9,3) orthogonal Vector(3, 1)).isFalse()
        Assertions.assertThat(Vector(9,3) orthogonal Vector(3, 2)).isFalse()
        Assertions.assertThat(Vector(9,3) orthogonal Vector.ZERO).isFalse()
        Assertions.assertThat(Vector.ZERO orthogonal Vector(9,3)).isFalse()
        Assertions.assertThat(Vector.ZERO orthogonal Vector.ZERO).isFalse()
    }

    @Test
    fun `test Vector toContinuous method`() {
        Assertions.assertThat(Vector(3,2).toContinuous()).isEqualTo(VectorD(3.0, 2.0))
    }
}