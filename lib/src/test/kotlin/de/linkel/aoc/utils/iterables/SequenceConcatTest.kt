package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SequenceConcatTest {
    @Test
    fun `1,2,3 can be prepended with 0`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).prepend(0).toList()
        ).isEqualTo(listOf(0,1,2,3))
    }

    @Test
    fun `1,2,3 can be extended with 4`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).append(4).toList()
        ).isEqualTo(listOf(1,2,3,4))
    }

    @Test
    fun `1,2,3 can be prepended with 0 and extended with 4`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).prepend(0).append(4).toList()
        ).isEqualTo(listOf(0,1,2,3,4))
    }

    @Test
    fun `1,2,3 can be extended with 4 and prepended with 0`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).append(4).prepend(0).toList()
        ).isEqualTo(listOf(0,1,2,3,4))
    }

    @Test
    fun `1,2,3 can be prepended with -1,0`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).prepend(sequenceOf(-1,0)).toList()
        ).isEqualTo(listOf(-1,0,1,2,3))
    }

    @Test
    fun `1,2,3 can be extended with 4,5`() {
        Assertions.assertThat(
            sequenceOf(1, 2, 3).append(sequenceOf(4,5)).toList()
        ).isEqualTo(listOf(1,2,3,4,5))
    }

    @Test
    fun `1,2 + 3 can be extended with 4 and prepended with 0`() {
        Assertions.assertThat(
            sequenceOf(1, 2).append(3).append(4).prepend(0).toList()
        ).isEqualTo(listOf(0,1,2,3,4))
    }

    @Test
    fun `1,2 + 3 can be extended with 4,5 and prepended with -1,0`() {
        Assertions.assertThat(
            sequenceOf(1, 2).append(3).append(sequenceOf(4,5)).prepend(sequenceOf(-1,0)).toList()
        ).isEqualTo(listOf(-1,0,1,2,3,4,5))
    }

    @Test
    fun `0,1 + 2,3 + 4,5 works`() {
        Assertions.assertThat(
            (sequenceOf(0, 1) + sequenceOf(2, 3) + sequenceOf(4, 5)).toList()
        ).isEqualTo(listOf(0,1,2,3,4,5))
    }
}
