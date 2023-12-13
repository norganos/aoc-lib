package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class FilterTest {
    @Test
    fun `withoutIndex with index works on empty list`() {
        Assertions.assertThat(
            emptyList<Int>().withoutIndex(0)
        ).isEqualTo(
            emptyList<Int>()
        )
    }

    @Test
    fun `withoutIndex works if given index is 0`() {
        Assertions.assertThat(
            listOf(1, 2, 3).withoutIndex(0)
        ).isEqualTo(
            listOf(2, 3)
        )
    }

    @Test
    fun `withoutIndex works if given index is last`() {
        Assertions.assertThat(
            listOf(1, 2, 3).withoutIndex(2)
        ).isEqualTo(
            listOf(1, 2)
        )
    }

    @Test
    fun `withoutIndex works if given index is in the middle`() {
        Assertions.assertThat(
            listOf(1, 2, 3).withoutIndex(1)
        ).isEqualTo(
            listOf(1, 3)
        )
    }

    @Test
    fun `withoutIndex works if given index is out of range`() {
        Assertions.assertThat(
            listOf(1, 2, 3).withoutIndex(12)
        ).isEqualTo(
            listOf(1, 2, 3)
        )
        Assertions.assertThat(
            listOf(1, 2, 3).withoutIndex(-1)
        ).isEqualTo(
            listOf(1, 2, 3)
        )
    }

    @Test
    fun `withoutIndexRange works if given range overlaps at the beginning`() {
        Assertions.assertThat(
            listOf(1, 2, 3, 4).withoutIndex(-1..1)
        ).isEqualTo(
            listOf(3, 4)
        )
    }

    @Test
    fun `withoutIndexRange works if given range overlaps at the end`() {
        Assertions.assertThat(
            listOf(1, 2, 3, 4).withoutIndex(3..5)
        ).isEqualTo(
            listOf(1, 2, 3)
        )
    }

    @Test
    fun `withoutIndexRange works if given range overlaps in the middle`() {
        Assertions.assertThat(
            listOf(1, 2, 3, 4).withoutIndex(1..2)
        ).isEqualTo(
            listOf(1, 4)
        )
    }
}
