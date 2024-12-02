package de.linkel.aoc.utils.geometry.plain.continuous

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class DimensionTests {

    @Test
    fun `test DimensionD constructor`() {
        val dimension = DimensionD(2.0, 3.0)
        Assertions.assertThat(dimension.width).isEqualTo(2.0)
        Assertions.assertThat(dimension.height).isEqualTo(3.0)
    }

    @Test
    fun `negative or zero dimension do not work`() {
        Assertions.assertThatThrownBy { DimensionD(-1.0, 1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { DimensionD(1.0, -1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { DimensionD(0.0, 1.0) }.isInstanceOf(IllegalArgumentException::class.java)
        Assertions.assertThatThrownBy { DimensionD(1.0, 0.0) }.isInstanceOf(IllegalArgumentException::class.java)
    }

    @Test
    fun `test DimensionD equals method`() {
        val dimension1 = DimensionD(2.0, 3.0)
        val dimension2 = DimensionD(2.0, 3.0)
        val dimension3 = DimensionD(3.0, 4.0)
        Assertions.assertThat(dimension1).isEqualTo(dimension2)
        Assertions.assertThat(dimension1).isNotEqualTo(dimension3)
    }

    @Test
    fun `test DimensionD area`() {
        Assertions.assertThat(DimensionD(3.0,2.0).area).isEqualTo(6.0)
    }
}