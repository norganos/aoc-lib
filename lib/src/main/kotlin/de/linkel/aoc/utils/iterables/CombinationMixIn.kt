package de.linkel.aoc.utils.iterables

fun <T> List<T>.combinationPairs(
    withSelf: Boolean = false,
    withMirrors: Boolean = false
): Sequence<Pair<T,T>> {
    val size = this.size
    val list = this
    return sequence {
        (0 until size)
            .forEach { i ->
                ((if (withMirrors) 0 else i) until size)
                    .forEach { j ->
                        if (withSelf || i != j) {
                            yield(Pair(list[i], list[j]))
                        }
                    }
            }
    }
}

fun <A, B> List<A>.combineWith(other: List<B>): Sequence<Pair<A, B>> {
    val list = this
    return sequence {
        list
            .forEach { a ->
                other.forEach { b ->
                        yield(Pair(a, b))
                    }
            }
    }
}

fun <T> List<List<T>>.combinations(): Sequence<List<T>> {
    val outer = this
    return sequence {
        val sizes = outer.map { it.size }
        val indices = outer.map { 0 }.toMutableList()
        while (indices.mapIndexed { i, o -> o < sizes[i] }.all { it }) {
            yield(
                indices.mapIndexed { i,o -> outer[i][o] }
            )
            if (outer.indices
                .firstOrNull { i ->
                    indices[i] = indices[i] + 1
                    if (indices[i] >= sizes[i]) {
                        indices[i] = 0
                        false
                    } else true
            } == null) {
                break
            }
        }
    }
}

fun <T> List<T>.permutations(maxSize: Int = -1): Sequence<List<T>> {
    val list = this
    return sequence {
        if (list.isEmpty() || maxSize == 0) {
            yield(emptyList())
        } else {
            list.indices.forEach { i ->
                val item = list[i]
                list.withoutIndex(i).permutations(maxSize - 1)
                    .forEach {
                        yield(it + item)
                    }
            }
        }
    }
}
