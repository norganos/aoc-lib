package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.DimensionD
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DimensionTests {

    @Test
    fun `test Dimension constructor`() {
        val dimension = Dimension(2, 3)
        Assertions.assertThat(dimension.width).isEqualTo(2)
        Assertions.assertThat(dimension.height).isEqualTo(3)
    }

    @Test
    fun `negative dimension do not work`() {
        Assertions.assertThatThrownBy { Dimension(-1, 1) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { Dimension(1, -1) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test Dimension equals method`() {
        val dimension1 = Dimension(2, 3)
        val dimension2 = Dimension(2, 3)
        val dimension3 = Dimension(3, 4)
        Assertions.assertThat(dimension1).isEqualTo(dimension2)
        Assertions.assertThat(dimension1).isNotEqualTo(dimension3)
    }

    @Test
    fun `test Dimension area`() {
        Assertions.assertThat(Dimension(3,2).area).isEqualTo(6)
    }

    @Test
    fun `test Dimension toContinuous method`() {
        Assertions.assertThat(Dimension(3,2).toContinuous()).isEqualTo(DimensionD(3.0, 2.0))
    }
}