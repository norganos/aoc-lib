package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class CombinationTest {
    @Test
    fun `empty list produces no combinations`() {
        Assertions.assertThat(emptyList<Int>().combinationPairs(withSelf = false, withMirrors = false).toSet()).isEmpty()
        Assertions.assertThat(emptyList<Int>().combinationPairs(withSelf = true, withMirrors = false).toSet()).isEmpty()
        Assertions.assertThat(emptyList<Int>().combinationPairs(withSelf = false, withMirrors = true).toSet()).isEmpty()
        Assertions.assertThat(emptyList<Int>().combinationPairs(withSelf = true, withMirrors = true).toSet()).isEmpty()
    }

    @Test
    fun `1,2,3 without self and without mirrors is produces 3 pairs`() {
        Assertions.assertThat(
            listOf(1, 2, 3)
                .combinationPairs(withSelf = false, withMirrors = false)
                .toSet()
        ).isEqualTo(
            setOf(
                1 to 2,
                1 to 3,
                2 to 3
            )
        )
    }

    @Test
    fun `1,2,3 with self and without mirrors is produces 6 pairs`() {
        Assertions.assertThat(
            listOf(1, 2, 3)
                .combinationPairs(withSelf = true, withMirrors = false)
                .toSet()
        ).isEqualTo(
            setOf(
                1 to 1,
                1 to 2,
                1 to 3,
                2 to 2,
                2 to 3,
                3 to 3
            )
        )
    }

    @Test
    fun `1,2,3 without self and with mirrors is produces 6 pairs`() {
        Assertions.assertThat(
            listOf(1, 2, 3)
                .combinationPairs(withSelf = false, withMirrors = true)
                .toSet()
        ).isEqualTo(
            setOf(
                1 to 2,
                1 to 3,
                2 to 1,
                2 to 3,
                3 to 1,
                3 to 2
            )
        )
    }

    @Test
    fun `1,2,3 with self and with mirrors is produces 9 pairs`() {
        Assertions.assertThat(
            listOf(1, 2, 3)
                .combinationPairs(withSelf = true, withMirrors = true)
                .toSet()
        ).isEqualTo(
            setOf(
                1 to 1,
                1 to 2,
                1 to 3,
                2 to 1,
                2 to 2,
                2 to 3,
                3 to 1,
                3 to 2,
                3 to 3
            )
        )
    }

    @Test
    fun `1,2,3 combined with a,b produces 6 elements`() {
        Assertions.assertThat(
            listOf('1','2','3')
                .combineWith(
                    listOf('a','b')
                )
                .toSet()
        ).isEqualTo(
            setOf(
                '1' to 'a',
                '1' to 'b',
                '2' to 'a',
                '2' to 'b',
                '3' to 'a',
                '3' to 'b'
            )
        )
    }

    @Test
    fun `1,2,3 combined with empty list is empty`() {
        Assertions.assertThat(
            listOf('1','2','3')
                .combineWith(
                    emptyList<Char>()
                )
                .toSet()
        ).isEmpty()
    }

    @Test
    fun `combinations of 3 * 1,2,3 are 27`() {
        Assertions.assertThat(
            List(3) { listOf(1,2,3) }
                .combinations()
                .toSet()
        ).isEqualTo(
            setOf(
                listOf(1, 1, 1),
                listOf(1, 1, 2),
                listOf(1, 1, 3),
                listOf(1, 2, 1),
                listOf(1, 2, 2),
                listOf(1, 2, 3),
                listOf(1, 3, 1),
                listOf(1, 3, 2),
                listOf(1, 3, 3),

                listOf(2, 1, 1),
                listOf(2, 1, 2),
                listOf(2, 1, 3),
                listOf(2, 2, 1),
                listOf(2, 2, 2),
                listOf(2, 2, 3),
                listOf(2, 3, 1),
                listOf(2, 3, 2),
                listOf(2, 3, 3),

                listOf(3, 1, 1),
                listOf(3, 1, 2),
                listOf(3, 1, 3),
                listOf(3, 2, 1),
                listOf(3, 2, 2),
                listOf(3, 2, 3),
                listOf(3, 3, 1),
                listOf(3, 3, 2),
                listOf(3, 3, 3),
            )
        )
    }

    @Test
    fun `permutations of 1 is 1`() {
        Assertions.assertThat(
            listOf(1)
                .permutations()
                .toSet()
        ).isEqualTo(
            setOf(
                listOf(1),
            )
        )
    }
    @Test
    fun `permutations of emptyList is list of emptyList`() {
        Assertions.assertThat(
            emptyList<Int>()
                .permutations()
                .toSet()
        ).isEqualTo(
            setOf(
                emptyList<Int>(),
            )
        )
    }

    @Test
    fun `permutations of 1,2,3`() {
        Assertions.assertThat(
            listOf(1,2,3)
                .permutations()
                .toSet()
        ).isEqualTo(
            setOf(
                listOf(1, 2, 3),
                listOf(1, 3, 2),
                listOf(2, 1, 3),
                listOf(2, 3, 1),
                listOf(3, 1, 2),
                listOf(3, 2, 1),
            )
        )
    }

    @Test
    fun `permutations of 1,1,2,3`() {
        Assertions.assertThat(
            listOf(1,1,2,3)
                .permutations()
                .toSet()
        ).isEqualTo(
            setOf(
                listOf(1, 1, 2, 3),
                listOf(1, 1, 3, 2),
                listOf(1, 2, 1, 3),
                listOf(1, 2, 3, 1),
                listOf(1, 3, 1, 2),
                listOf(1, 3, 2, 1),
                listOf(1, 1, 2, 3),
                listOf(1, 1, 3, 2),
                listOf(2, 1, 1, 3),
                listOf(2, 1, 3, 1),
                listOf(3, 1, 1, 2),
                listOf(3, 1, 2, 1),
                listOf(1, 2, 1, 3),
                listOf(1, 3, 1, 2),
                listOf(2, 1, 1, 3),
                listOf(2, 3, 1, 1),
                listOf(3, 1, 1, 2),
                listOf(3, 2, 1, 1),
                listOf(1, 2, 3, 1),
                listOf(1, 3, 2, 1),
                listOf(2, 1, 3, 1),
                listOf(2, 3, 1, 1),
                listOf(3, 1, 2, 1),
                listOf(3, 2, 1, 1),
            )
        )
    }

    @Test
    fun `permutations of 2 elements of 1,2,3`() {
        Assertions.assertThat(
            listOf(1,2,3)
                .permutations(2)
                .toSet()
        ).isEqualTo(
            setOf(
                listOf(1, 2),
                listOf(1, 3),
                listOf(2, 1),
                listOf(2, 3),
                listOf(3, 1),
                listOf(3, 2),
            )
        )
    }
}
