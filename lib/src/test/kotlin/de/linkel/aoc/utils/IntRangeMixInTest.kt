package de.linkel.aoc.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntRangeMixInTest {
    @Test
    fun `1 to 3 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (1..3).intersects(4..6)
        ).isFalse()
    }

    @Test
    fun `10 to 30 and 4 to 6 do not intersect`() {
        Assertions.assertThat(
            (10..30).intersects(4..6)
        ).isFalse()
    }

    @Test
    fun `1 to 3 and 2 to 4 intersect`() {
        Assertions.assertThat(
            (1..3).intersects(2..4)
        ).isTrue()
    }

    @Test
    fun `1 to 3 and 3 to 4 intersect`() {
        Assertions.assertThat(
            (1..3).intersects(3..4)
        ).isTrue()
    }

    @Test
    fun `-1 to 1 and 0 to 4 intersect`() {
        Assertions.assertThat(
            (-1..1).intersects(0..4)
        ).isTrue()
    }

    @Test
    fun `-3 to -1 and -2 to 4 intersect`() {
        Assertions.assertThat(
            (-3..-1).intersects(-2..4)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 5 to 10 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(5..10)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 4 to 5 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(4..5)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 3 to 4 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(3..4)
        ).isTrue()
    }

    @Test
    fun `1 to 5 and 5 to 5 intersect`() {
        Assertions.assertThat(
            (1..5).intersects(5..5)
        ).isTrue()
    }

    @Test
    fun `2 to 4 extended by 1 at front is 1 to 4`() {
        Assertions.assertThat(
            (2..4).extend(front = 1)
        ).isEqualTo(1..4)
    }

    @Test
    fun `2 to 4 extended by 2 at front is 0 to 4`() {
        Assertions.assertThat(
            (2..4).extend(front = 2)
        ).isEqualTo(0..4)
    }

    @Test
    fun `2 to 4 extended by 3 at front is -1 to 4`() {
        Assertions.assertThat(
            (2..4).extend(front = 3)
        ).isEqualTo(-1..4)
    }

    @Test
    fun `2 to 4 extended by 1 at back is 2 to 5`() {
        Assertions.assertThat(
            (2..4).extend(back = 1)
        ).isEqualTo(2..5)
    }

    @Test
    fun `2 to 4 extended by 2 at back is 2 to 6`() {
        Assertions.assertThat(
            (2..4).extend(back = 2)
        ).isEqualTo(2..6)
    }

    @Test
    fun `2 to 4 extended by 2 at front and back is 0 to 6`() {
        Assertions.assertThat(
            (2..4).extend(front = 2, back = 2)
        ).isEqualTo(0..6)
    }
}
