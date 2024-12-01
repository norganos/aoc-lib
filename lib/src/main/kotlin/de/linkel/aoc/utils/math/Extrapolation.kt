package de.linkel.aoc.utils.math


fun List<Long>.toExtrapolation() = Extrapolation(this)

class Extrapolation(
    inputs: List<Long>
) {
    private val progressions = Differential(inputs).prepare().lasts
    init {
        assert(progressions.first() == 0L)
    }

    fun extrapolate(steps: Int): Long {
        val vals = progressions.toMutableList()
        repeat(steps) {
            vals.indices.forEach { i ->
                if (i < vals.lastIndex) {
                    vals[i+1] += vals[i]
                }
            }
        }
        return vals.last()
    }

    class Differential(
        input: List<Long>,
        val parent: Differential? = null
    ) {
        var values = input.toMutableList()
            private set

        val lasts get(): List<Long> = listOf(values.last()) + (parent?.lasts ?: emptyList())

        fun prepare(): Differential {
            return if (values.last() == 0L) this
            else differentiate().prepare()
        }

        fun differentiate(): Differential {
            val diffs = values
                .windowed(2)
                .map { (a, b) -> b - a }
                .toList()
            if (diffs.isEmpty())
                throw Exception("not enough input iterations")
            return Differential(
                input = diffs,
                parent = this
            )
        }
    }

}
