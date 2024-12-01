package de.linkel.aoc.utils.math

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class ExtrapolationTest {
    @Test
    fun `sequence of squares can be extrapolated`() {
        val extrapolation = listOf(1L, 4L, 9L, 16L).toExtrapolation()
        Assertions.assertThat(extrapolation.extrapolate(1)).isEqualTo(25L)
        Assertions.assertThat(extrapolation.extrapolate(2)).isEqualTo(36L)
        Assertions.assertThat(extrapolation.extrapolate(3)).isEqualTo(49L)
    }

    @Test
    fun `sequence of cubes can be extrapolated`() {
        val extrapolation = listOf(1L, 8L, 27L, 64L, 125L).toExtrapolation()
        Assertions.assertThat(extrapolation.extrapolate(1)).isEqualTo(216L)
        Assertions.assertThat(extrapolation.extrapolate(2)).isEqualTo(343L)
    }

    @Test
    fun `linear sequence can be extrapolated`() {
        val extrapolation = listOf(5L, 6L, 7L, 8L).toExtrapolation()
        Assertions.assertThat(extrapolation.extrapolate(1)).isEqualTo(9L)
        Assertions.assertThat(extrapolation.extrapolate(2)).isEqualTo(10L)
    }

    @Test
    fun `negative sequence of squares can be extrapolated`() {
        val extrapolation = listOf(-1L, -4L, -9L, -16L).toExtrapolation()
        Assertions.assertThat(extrapolation.extrapolate(1)).isEqualTo(-25L)
    }

    @Test
    fun `square sequence with offset can be extrapolated`() {
        val extrapolation = listOf(11L, 14L, 19L, 26L).toExtrapolation()
        Assertions.assertThat(extrapolation.extrapolate(1)).isEqualTo(35L)
    }
}
