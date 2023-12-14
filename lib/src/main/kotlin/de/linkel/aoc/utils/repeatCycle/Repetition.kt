package de.linkel.aoc.utils.repeatCycle

class RepetitionContext<T, H>(
    private val iterations: Int,
    private val transformation: (state: T) -> T,
    private val hash: ((state: T) -> H)? = null,
    private val maxHashes: Int = 100,
    private val iterationsBeforeHashing: Int = 50
) {
    fun <HO> withHashing(lambda: (state: T) -> HO): RepetitionContext<T, HO> {
        return RepetitionContext<T, HO>(
            iterations = this.iterations,
            transformation = this.transformation,
            hash = lambda,
            maxHashes = this.maxHashes,
            iterationsBeforeHashing = this.iterationsBeforeHashing
        )
    }
    fun withMemorySize(value: Int): RepetitionContext<T, H> {
        return RepetitionContext(
            iterations = this.iterations,
            transformation = this.transformation,
            hash = this.hash,
            maxHashes = value,
            iterationsBeforeHashing = this.iterationsBeforeHashing
        )
    }
    fun withMinimumTransformations(value: Int): RepetitionContext<T, H> {
        return RepetitionContext(
            iterations = this.iterations,
            transformation = this.transformation,
            hash = this.hash,
            maxHashes = this.maxHashes,
            iterationsBeforeHashing = value
        )
    }
    fun withIterations(value: Int): RepetitionContext<T, H> {
        return RepetitionContext(
            iterations = value,
            transformation = this.transformation,
            hash = this.hash,
            maxHashes = this.maxHashes,
            iterationsBeforeHashing = value
        )
    }

    fun transform(input: T): T {
        val lastIterations = mutableListOf<H>()
        var i = 0
        var state: T = input
        val hashLambda = hash
        while (i < iterations) {
            state = transformation(state)
            if (iterationsBeforeHashing > i && hashLambda != null) {
                val h = hashLambda(state)
                val hi = lastIterations.indexOf(h)
                if (hi > -1) {
                    val cycleLength = hi + 1
                    while (i + cycleLength < iterations) {
                        i += cycleLength
                    }

                    lastIterations.clear()
                }
                lastIterations.add(0, h)
                if (lastIterations.size > maxHashes) {
                    lastIterations.removeAt(maxHashes)
                }
            }
            i++
        }
        return state
    }
}
