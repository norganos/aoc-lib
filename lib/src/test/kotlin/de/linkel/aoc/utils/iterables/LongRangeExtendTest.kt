package de.linkel.aoc.utils.iterables

import de.linkel.aoc.utils.mixins.extend
import de.linkel.aoc.utils.mixins.move
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class LongRangeExtendTest {
    @Test
    fun `2 to 4 extended by 1 at front is 1 to 4`() {
        Assertions.assertThat(
            (2L..4L).extend(front = 1L)
        ).isEqualTo(1L..4L)
    }

    @Test
    fun `2 to 4 extended by 2 at front is 0 to 4`() {
        Assertions.assertThat(
            (2L..4L).extend(front = 2L)
        ).isEqualTo(0L..4L)
    }

    @Test
    fun `2 to 4 extended by 3 at front is -1 to 4`() {
        Assertions.assertThat(
            (2L..4L).extend(front = 3L)
        ).isEqualTo(-1L..4L)
    }

    @Test
    fun `2 to 4 extended by 1 at back is 2 to 5`() {
        Assertions.assertThat(
            (2L..4L).extend(back = 1L)
        ).isEqualTo(2L..5L)
    }

    @Test
    fun `2 to 4 extended by 2 at back is 2 to 6`() {
        Assertions.assertThat(
            (2L..4L).extend(back = 2L)
        ).isEqualTo(2L..6L)
    }

    @Test
    fun `2 to 4 extended by 2 at front and back is 0 to 6`() {
        Assertions.assertThat(
            (2L..4L).extend(front = 2L, back = 2L)
        ).isEqualTo(0L..6L)
    }

    @Test
    fun `2 to 4 moved by 2 is 4 to 6`() {
        Assertions.assertThat(
            (2L..4L).move(2L)
        ).isEqualTo(4L..6L)
    }

    @Test
    fun `2 to 4 moved by -1 is 1 to 3`() {
        Assertions.assertThat(
            (2L..4L).move(-1L)
        ).isEqualTo(1L..3L)
    }
}
