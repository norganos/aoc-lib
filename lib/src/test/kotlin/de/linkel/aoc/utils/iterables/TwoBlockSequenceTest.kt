package de.linkel.aoc.utils.iterables

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TwoBlockSequenceTest {
    @Test
    fun `empty sequence calls both blocks with empty sequence`() {
        Assertions.assertThat(
            emptySequence<String>()
                .inTwoBlocks(String::isEmpty,
                    { seq -> seq.count() },
                    { a, seq -> a to seq.count() }
                )
        ).isEqualTo(0 to 0)
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
                .inTwoBlocks(String::isEmpty,
                    { seq -> seq.toList() },
                    { a, seq -> listOf(a, seq.toList()) }
                )
        ).isEqualTo(listOf(
            listOf("erste zeile", "zweite zeile"),
            listOf("dritte zeile", "vierte zeile")
        ))
    }

    @Test
    fun `empty iterable calls both blocks with empty sequence`() {
        Assertions.assertThat(
            emptyList<String>()
                .inTwoBlocks(String::isEmpty,
                    { seq -> seq.count() },
                    { a, seq -> a to seq.count() }
                )
        ).isEqualTo(0 to 0)
    }

    @Test
    fun `iterable of lines can be split on empty line`() {
        Assertions.assertThat(
            """
                erste zeile
                zweite zeile
                
                dritte zeile
                vierte zeile
            """.trimIndent()
                .lineSequence()
                .toList()
                .inTwoBlocks(String::isEmpty,
                    { seq -> seq.toList() },
                    { a, seq -> listOf(a, seq.toList()) }
                )
        ).isEqualTo(listOf(
            listOf("erste zeile", "zweite zeile"),
            listOf("dritte zeile", "vierte zeile")
        ))
    }
}
