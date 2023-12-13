package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class IntRangeExtendTest {
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

    @Test
    fun `2 to 4 moved by 2 is 4 to 6`() {
        Assertions.assertThat(
            (2..4).move(2)
        ).isEqualTo(4..6)
    }

    @Test
    fun `2 to 4 moved by -1 is 1 to 3`() {
        Assertions.assertThat(
            (2..4).move(-1)
        ).isEqualTo(1..3)
    }
}
