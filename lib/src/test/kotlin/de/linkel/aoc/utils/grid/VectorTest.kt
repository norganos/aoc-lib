package de.linkel.aoc.utils.grid

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VectorTest {
    @Test
    fun `basic properties work on default directions`() {
        Assertions.assertThat(Vector.NORTH.isVertical).isTrue()
        Assertions.assertThat(Vector.NORTH.isHorizontal).isFalse()
        Assertions.assertThat(Vector.SOUTH.isVertical).isTrue()
        Assertions.assertThat(Vector.SOUTH.isHorizontal).isFalse()
        Assertions.assertThat(Vector.WEST.isVertical).isFalse()
        Assertions.assertThat(Vector.WEST.isHorizontal).isTrue()
        Assertions.assertThat(Vector.EAST.isVertical).isFalse()
        Assertions.assertThat(Vector.EAST.isHorizontal).isTrue()
        Assertions.assertThat(Vector.NORTH.turnAround()).isEqualTo(Vector.SOUTH)
        Assertions.assertThat(Vector.NORTH.turnLeft()).isEqualTo(Vector.WEST)
        Assertions.assertThat(Vector.NORTH.turnRight()).isEqualTo(Vector.EAST)
        Assertions.assertThat(Vector.SOUTH.turnAround()).isEqualTo(Vector.NORTH)
        Assertions.assertThat(Vector.SOUTH.turnLeft()).isEqualTo(Vector.EAST)
        Assertions.assertThat(Vector.SOUTH.turnRight()).isEqualTo(Vector.WEST)
        Assertions.assertThat(Vector.WEST.turnAround()).isEqualTo(Vector.EAST)
        Assertions.assertThat(Vector.WEST.turnLeft()).isEqualTo(Vector.SOUTH)
        Assertions.assertThat(Vector.WEST.turnRight()).isEqualTo(Vector.NORTH)
        Assertions.assertThat(Vector.EAST.turnAround()).isEqualTo(Vector.WEST)
        Assertions.assertThat(Vector.EAST.turnLeft()).isEqualTo(Vector.NORTH)
        Assertions.assertThat(Vector.EAST.turnRight()).isEqualTo(Vector.SOUTH)
        Assertions.assertThat(Vector.NORTH.manhattenDistance).isEqualTo(1)
        Assertions.assertThat(Vector.SOUTH.manhattenDistance).isEqualTo(1)
        Assertions.assertThat(Vector.WEST.manhattenDistance).isEqualTo(1)
        Assertions.assertThat(Vector.EAST.manhattenDistance).isEqualTo(1)
        Assertions.assertThat(Vector.NORTH.maximumAxisDistance).isEqualTo(1)
        Assertions.assertThat(Vector.SOUTH.maximumAxisDistance).isEqualTo(1)
        Assertions.assertThat(Vector.WEST.maximumAxisDistance).isEqualTo(1)
        Assertions.assertThat(Vector.EAST.maximumAxisDistance).isEqualTo(1)
    }
    @Test
    fun `basic properties work on arbitrary`() {
        val vector1 = Vector(2, 1)
        val vector2 = Vector(1, -2)
        val vector3 = Vector(4, 5)
        Assertions.assertThat(vector1.isHorizontal).isFalse()
        Assertions.assertThat(vector1.isVertical).isFalse()
        Assertions.assertThat(vector1.manhattenDistance).isEqualTo(3)
        Assertions.assertThat(vector2.isHorizontal).isFalse()
        Assertions.assertThat(vector2.isVertical).isFalse()
        Assertions.assertThat(vector2.manhattenDistance).isEqualTo(3)
        Assertions.assertThat(vector1 orthogonal vector2).isTrue()
        Assertions.assertThat(vector1 orthogonal vector3).isFalse()
        Assertions.assertThat(vector2 orthogonal vector3).isFalse()
        Assertions.assertThat(vector1 parallel vector2).isFalse()
        Assertions.assertThat(vector1 parallel vector3).isFalse()
        Assertions.assertThat(vector2 parallel vector3).isFalse()
        Assertions.assertThat(vector2 parallel vector3).isFalse()
        Assertions.assertThat(vector1.turnLeft()).isEqualTo(vector2)
        Assertions.assertThat(vector2.turnRight()).isEqualTo(vector1)
    }

    @Test
    fun `parallel works`() {
        Assertions.assertThat(Vector(6, 3) parallel Vector(-2, -1)).isTrue()
        Assertions.assertThat(Vector(6, 3) parallel Vector(2, 1)).isTrue()
        Assertions.assertThat(Vector.NORTH parallel Vector.SOUTH).isTrue()
        Assertions.assertThat(Vector.NORTH parallel Vector.WEST).isFalse()
        Assertions.assertThat(Vector(6, 3) parallel Vector(4, 5)).isFalse()
    }
    @Test
    fun `notParallel works`() {
        Assertions.assertThat(Vector(6, 3) notParallel Vector(-2, -1)).isFalse()
        Assertions.assertThat(Vector(6, 3) notParallel Vector(2, 1)).isFalse()
        Assertions.assertThat(Vector.NORTH notParallel Vector.SOUTH).isFalse()
        Assertions.assertThat(Vector.NORTH notParallel Vector.WEST).isTrue()
        Assertions.assertThat(Vector(6, 3) notParallel Vector(4, 5)).isTrue()
    }
    @Test
    fun `orthogonal works`() {
        Assertions.assertThat(Vector(6, 3) orthogonal Vector(1, 2)).isFalse()
        Assertions.assertThat(Vector(6, 3) orthogonal Vector(-1, 2)).isTrue()
        Assertions.assertThat(Vector.NORTH orthogonal  Vector.SOUTH).isFalse()
        Assertions.assertThat(Vector.NORTH orthogonal Vector.WEST).isTrue()
        Assertions.assertThat(Vector(6, 3) orthogonal Vector(4, 5)).isFalse()
    }
    @Test
    fun `contains works`() {
        Assertions.assertThat(Vector(2, 1) in Vector(6, 3)).isTrue()
        Assertions.assertThat(Vector(6, 3) in Vector(6, 3)).isTrue()
        Assertions.assertThat(Vector(6, 3) in Vector(2, 1)).isFalse()
        Assertions.assertThat(Vector(-2, -1) in Vector(2, 1)).isFalse()
        Assertions.assertThat(Vector(-2, -1) in Vector(6, 3)).isFalse()
        Assertions.assertThat(Vector(2, -1) in Vector(6, 3)).isFalse()
        Assertions.assertThat(Vector(1, 0) in Vector(2, 0)).isTrue()
        Assertions.assertThat(Vector( 0, 1) in Vector(0, 3)).isTrue()
        Assertions.assertThat(Vector( 0, 0) in Vector(0, 3)).isFalse()
        Assertions.assertThat(Vector( 0, 0) in Vector(0, 0)).isTrue()
    }
    @Test
    fun `addition works`() {
        Assertions.assertThat(Vector(6, 3) + Vector(1, -2)).isEqualTo(Vector(7, 1))
        Assertions.assertThat(Vector.NORTH + Vector.SOUTH).isEqualTo(Vector.ZERO)
        Assertions.assertThat(Vector.NORTH + Vector.EAST).isEqualTo(Vector(1, -1))
    }
    @Test
    fun `subtraction works`() {
        Assertions.assertThat(Vector(6, 3) - Vector(1, -2)).isEqualTo(Vector(5, 5))
        Assertions.assertThat(Vector.NORTH - Vector.NORTH).isEqualTo(Vector.ZERO)
        Assertions.assertThat(Vector.NORTH - Vector.SOUTH).isEqualTo(Vector.NORTH * 2)
        Assertions.assertThat(Vector.NORTH - Vector.EAST).isEqualTo(Vector(-1, -1))
    }
    @Test
    fun `multiplication works`() {
        Assertions.assertThat(Vector(6, 3) * 2).isEqualTo(Vector(12, 6))
        Assertions.assertThat(Vector.NORTH * 0).isEqualTo(Vector.ZERO)
        Assertions.assertThat(Vector.NORTH * 1).isEqualTo(Vector.NORTH)
        Assertions.assertThat(Vector.NORTH * -1).isEqualTo(Vector.SOUTH)
    }
    @Test
    fun `division works`() {
        Assertions.assertThat(Vector(6, 3) / 3).isEqualTo(Vector(2, 1))
        Assertions.assertThatThrownBy { Vector(6, 3) / 2 }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Vector.NORTH / 0 }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThat(Vector.NORTH / -1).isEqualTo(Vector.SOUTH)
    }
}
