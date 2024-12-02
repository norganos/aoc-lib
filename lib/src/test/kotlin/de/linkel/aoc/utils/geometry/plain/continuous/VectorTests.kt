package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class VectorTests {

    @Test
    fun `test VectorD constructor`() {
        val point = VectorD(2.0,3.0)
        Assertions.assertThat(point.deltaX).isEqualTo(2.0)
        Assertions.assertThat(point.deltaY).isEqualTo(3.0)
    }

    @Test
    fun `test VectorD equals method`() {
        val vector1 = VectorD(2.0,3.0)
        val vector2 = VectorD(2.0,3.0)
        val vector3 = VectorD(3.0,4.0)
        Assertions.assertThat(vector1).isEqualTo(vector2)
        Assertions.assertThat(vector1).isNotEqualTo(vector3)
    }
    
    @Test
    fun `test VectorD distance calculation`() {
        Assertions.assertThat(VectorD(3.0,4.0).length).isEqualTo(5.0)
    }

    @Test
    fun `test VectorD maximumAxisDistance calculation`() {
        Assertions.assertThat(VectorD(3.0,4.0).maximumAxisDistance).isEqualTo(4.0)
    }

    @Test
    fun `test VectorD isVertical calculation`() {
        Assertions.assertThat(VectorD(3.0,4.0).isVertical).isFalse()
        Assertions.assertThat(VectorD(0.0,4.0).isVertical).isTrue()
    }

    @Test
    fun `test VectorD isHorizontal calculation`() {
        Assertions.assertThat(VectorD(3.0,4.0).isHorizontal).isFalse()
        Assertions.assertThat(VectorD(3.0,0.0).isHorizontal).isTrue()
    }

    @Test
    fun `test VectorD plus method`() {
        val vector1 = VectorD(1.0,2.0)
        Assertions.assertThat(vector1 + VectorD.NORTH).isEqualTo(VectorD(1.0,1.0))
        Assertions.assertThat(vector1 + VectorD.SOUTH).isEqualTo(VectorD(1.0,3.0))
        Assertions.assertThat(vector1 + VectorD.WEST).isEqualTo(VectorD(0.0,2.0))
        Assertions.assertThat(vector1 + VectorD.EAST).isEqualTo(VectorD(2.0,2.0))
        Assertions.assertThat(vector1 + VectorD.NORTH_WEST).isEqualTo(VectorD(0.0,1.0))
        Assertions.assertThat(vector1 + VectorD.NORTH_EAST).isEqualTo(VectorD(2.0,1.0))
        Assertions.assertThat(vector1 + VectorD.SOUTH_WEST).isEqualTo(VectorD(0.0,3.0))
        Assertions.assertThat(vector1 + VectorD.SOUTH_EAST).isEqualTo(VectorD(2.0,3.0))

        Assertions.assertThat(VectorD.ZERO + VectorD.NORTH).isEqualTo(VectorD(0.0,-1.0))
        Assertions.assertThat(VectorD.ZERO + VectorD.WEST).isEqualTo(VectorD(-1.0,0.0))

        Assertions.assertThat(VectorD.NORTH + VectorD.WEST).isEqualTo(VectorD.NORTH_WEST)
        Assertions.assertThat(VectorD.SOUTH_WEST + VectorD.EAST).isEqualTo(VectorD.SOUTH)
    }

    @Test
    fun `test VectorD minus method`() {
        val vector1 = VectorD(1.0,2.0)
        Assertions.assertThat(vector1 - VectorD.NORTH).isEqualTo(VectorD(1.0,3.0))
        Assertions.assertThat(vector1 - VectorD.SOUTH).isEqualTo(VectorD(1.0,1.0))
        Assertions.assertThat(vector1 - VectorD.WEST).isEqualTo(VectorD(2.0,2.0))
        Assertions.assertThat(vector1 - VectorD.EAST).isEqualTo(VectorD(0.0,2.0))
        Assertions.assertThat(vector1 - VectorD.NORTH_WEST).isEqualTo(VectorD(2.0,3.0))
        Assertions.assertThat(vector1 - VectorD.NORTH_EAST).isEqualTo(VectorD(0.0,3.0))
        Assertions.assertThat(vector1 - VectorD.SOUTH_WEST).isEqualTo(VectorD(2.0,1.0))
        Assertions.assertThat(vector1 - VectorD.SOUTH_EAST).isEqualTo(VectorD(0.0,1.0))

        Assertions.assertThat(VectorD.ZERO - VectorD.NORTH).isEqualTo(VectorD(0.0,1.0))
        Assertions.assertThat(VectorD.ZERO - VectorD.WEST).isEqualTo(VectorD(1.0,0.0))
    }

    @Test
    fun `test VectorD times method`() {
        Assertions.assertThat(VectorD(3.0,1.0) * 3.0).isEqualTo(VectorD(9.0,3.0))
        Assertions.assertThat(VectorD(3.0,-1.0) * 2.0).isEqualTo(VectorD(6.0,-2.0))
        Assertions.assertThat(VectorD(-3.0,1.0) * 2.0).isEqualTo(VectorD(-6.0,2.0))
        Assertions.assertThat(VectorD.ZERO * 2.0).isEqualTo(VectorD.ZERO)
    }

    @Test
    fun `test VectorD turnClockwise method`() {
        Assertions.assertThat(VectorD(3.0,1.0).turnClockwise()).isEqualTo(VectorD(-1.0,3.0))
        Assertions.assertThat(VectorD(-1.0,-1.0).turnClockwise()).isEqualTo(VectorD(1.0,-1.0))
        val vector1 = VectorD(3.0,1.0)
        Assertions.assertThat(vector1.turnClockwise().turnClockwise().turnClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnClockwise().turnCounterClockwise()).isEqualTo(vector1)
        Assertions.assertThat(VectorD.ZERO.turnClockwise()).isEqualTo(VectorD.ZERO)
    }

    @Test
    fun `test VectorD turnCounterClockwise method`() {
        Assertions.assertThat(VectorD(3.0,1.0).turnCounterClockwise()).isEqualTo(VectorD(1.0,-3.0))
        Assertions.assertThat(VectorD(-1.0,-1.0).turnCounterClockwise()).isEqualTo(VectorD(-1.0,1.0))
        val vector1 = VectorD(3.0,1.0)
        Assertions.assertThat(vector1.turnCounterClockwise().turnCounterClockwise().turnCounterClockwise().turnCounterClockwise()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnCounterClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(VectorD.ZERO.turnCounterClockwise()).isEqualTo(VectorD.ZERO)
    }

    @Test
    fun `test VectorD turnAround method`() {
        Assertions.assertThat(VectorD(3.0,1.0).turnAround()).isEqualTo(VectorD(-3.0,-1.0))
        Assertions.assertThat(-VectorD(3.0,1.0)).isEqualTo(VectorD(-3.0,-1.0))
        val vector1 = VectorD(3.0,1.0)
        Assertions.assertThat(vector1.turnAround().turnAround()).isEqualTo(vector1)
        Assertions.assertThat(vector1.turnAround().turnClockwise().turnClockwise()).isEqualTo(vector1)
        Assertions.assertThat(VectorD(-1.0,-1.0).turnAround()).isEqualTo(VectorD(1.0,1.0))
        Assertions.assertThat(-VectorD(-1.0,-1.0)).isEqualTo(VectorD(1.0,1.0))
        Assertions.assertThat(VectorD.ZERO.turnAround()).isEqualTo(VectorD.ZERO)
        Assertions.assertThat(-VectorD.ZERO).isEqualTo(VectorD.ZERO)
    }

    @Test
    fun `test VectorD div method`() {
        Assertions.assertThat(VectorD(9.0,3.0) / 3.0).isEqualTo(VectorD(3.0,1.0))
        Assertions.assertThat(VectorD(6.0,4.0) / 2.0).isEqualTo(VectorD(3.0,2.0))
        Assertions.assertThatThrownBy { VectorD(9.0,3.0) / 0.0 }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThat(VectorD(5.0,4.0) / 2.0).isEqualTo(VectorD(2.5, 2.0))
    }

    @Test
    fun `test VectorD contains method`() {
        Assertions.assertThat(VectorD(3.0,1.0) in VectorD(9.0,3.0)).isTrue()
        Assertions.assertThat(VectorD(6.0,2.0) in VectorD(9.0,3.0)).isTrue()
        Assertions.assertThat(VectorD(9.0,3.0) in VectorD(9.0,3.0)).isTrue()
        Assertions.assertThat(VectorD(3.0,-1.0) in VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD(-3.0,1.0) in VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD(2.0,1.0) in VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD(-3.0,-1.0) in VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD(9.0,3.0) in VectorD(6.0,2.0)).isFalse()
        Assertions.assertThat(VectorD.ZERO in VectorD(6.0,2.0)).isFalse()
        Assertions.assertThat(VectorD.NORTH in VectorD.ZERO).isFalse()
        Assertions.assertThat(VectorD.ZERO in VectorD.ZERO).isTrue()
    }

    @Test
    fun `test VectorD parallel method`() {
        Assertions.assertThat(VectorD(9.0,3.0) parallel VectorD(3.0,1.0)).isTrue()
        Assertions.assertThat(VectorD(3.0,1.0) parallel VectorD(6.0,2.0)).isTrue()
        Assertions.assertThat(VectorD(3.0,1.0) parallel VectorD(-6.0,-2.0)).isTrue()
        Assertions.assertThat(VectorD(9.0,3.0) parallel VectorD(3.0,2.0)).isFalse()
        Assertions.assertThat(VectorD(9.0,3.0) parallel VectorD.ZERO).isFalse()
        Assertions.assertThat(VectorD.ZERO parallel VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD.ZERO parallel VectorD.ZERO).isTrue()
    }

    @Test
    fun `test VectorD notParallel method`() {
        Assertions.assertThat(VectorD(9.0,3.0) notParallel VectorD(3.0,1.0)).isFalse()
        Assertions.assertThat(VectorD(3.0,1.0) notParallel VectorD(6.0,2.0)).isFalse()
        Assertions.assertThat(VectorD(-3.0,-1.0) notParallel VectorD(6.0,2.0)).isFalse()
        Assertions.assertThat(VectorD(9.0,3.0) notParallel VectorD(3.0,2.0)).isTrue()
        Assertions.assertThat(VectorD(9.0,3.0) notParallel VectorD.ZERO).isTrue()
        Assertions.assertThat(VectorD.ZERO notParallel VectorD(9.0,3.0)).isTrue()
        Assertions.assertThat(VectorD.ZERO notParallel VectorD.ZERO).isFalse()
    }

    @Test
    fun `test VectorD orthogonal method`() {
        Assertions.assertThat(VectorD(9.0,3.0) orthogonal VectorD(-1.0,3.0)).isTrue()
        Assertions.assertThat(VectorD(3.0,1.0) orthogonal VectorD(2.0,-6.0)).isTrue()
        Assertions.assertThat(VectorD(9.0,3.0) orthogonal VectorD(3.0,1.0)).isFalse()
        Assertions.assertThat(VectorD(9.0,3.0) orthogonal VectorD(3.0,2.0)).isFalse()
        Assertions.assertThat(VectorD(9.0,3.0) orthogonal VectorD.ZERO).isFalse()
        Assertions.assertThat(VectorD.ZERO orthogonal VectorD(9.0,3.0)).isFalse()
        Assertions.assertThat(VectorD.ZERO orthogonal VectorD.ZERO).isFalse()
    }
}