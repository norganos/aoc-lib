package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class SequenceSplitTest {
    @Test
    fun `1-10 can be split on numbers dividable by 3`() {
        Assertions.assertThat(
            (1..10).asSequence().split { it % 3 == 0 }
                .toList()
        ).isEqualTo(listOf(
            listOf(1, 2),
            listOf(4, 5),
            listOf(7, 8),
            listOf(10)
        ))
    }

    @Test
    fun `sequence of lines can be split on empty line`() {
        Assertions.assertThat(
            """
                erste zeile
                zweite zeile
                
                dritte zeile
                vierte zeile
            """.trimIndent()
                .lineSequence()
                .split(String::isEmpty)
                .toList()
        ).isEqualTo(listOf(
            listOf("erste zeile", "zweite zeile"),
            listOf("dritte zeile", "vierte zeile")
        ))
    }
}
