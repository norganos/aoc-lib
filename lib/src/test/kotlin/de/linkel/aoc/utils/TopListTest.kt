package de.linkel.aoc.utils

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class TopListTest {
    @Test
    fun `empty top 3 list works`() {
        val top3 = TopList<Int>(3)
        Assertions.assertThat(top3).hasSize(0)
        Assertions.assertThat(top3.isEmpty()).isTrue()
    }

    @Test
    fun `default collection methods work`() {
        val top3 = TopList<Int>(3).plus(listOf(1, 2, 5, 3, 4))
        Assertions.assertThat(top3).hasSize(3)
        Assertions.assertThat(top3).contains(5)
        Assertions.assertThat(top3).contains(4)
        Assertions.assertThat(top3).contains(3)
        Assertions.assertThat(top3.isEmpty()).isFalse()
        Assertions.assertThat(top3.contains(3)).isTrue()
        Assertions.assertThat(top3.containsAll(listOf(5, 4))).isTrue()
        Assertions.assertThat(top3.first()).isEqualTo(5)
    }

    @Test
    fun `top 3 list with 3 elements in 2 batches works`() {
        val top3 = TopList<Int>(3).plus(listOf(1, 2, 5)).plus(listOf(3, 4)).toList()
        Assertions.assertThat(top3).hasSize(3)
        Assertions.assertThat(top3).contains(5)
        Assertions.assertThat(top3).contains(4)
        Assertions.assertThat(top3).contains(3)
        Assertions.assertThat(top3.first()).isEqualTo(5)
    }

    @Test
    fun `top 3 list with 1 element works`() {
        val top3 = TopList<Int>(3).plus(23).toList()
        Assertions.assertThat(top3).hasSize(1)
        Assertions.assertThat(top3).contains(23)
    }

    @Test
    fun `top 3 list with 1 element in list works`() {
        val top3 = TopList<Int>(3).plus(listOf(23)).toList()
        Assertions.assertThat(top3).hasSize(1)
        Assertions.assertThat(top3).contains(23)
    }
}
