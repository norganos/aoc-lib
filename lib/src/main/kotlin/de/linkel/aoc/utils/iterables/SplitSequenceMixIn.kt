package de.linkel.aoc.utils.iterables


fun <T> Sequence<T>.split(predicate: (T) -> Boolean): Sequence<List<T>> {
    val input = this
    return sequence {
        val buffer = mutableListOf<T>()
        input.forEach { element ->
            if (predicate(element)) {
                yield(buffer.toList())
                buffer.clear()
            } else {
                buffer.add(element)
            }
        }
        if (buffer.isNotEmpty()) {
            yield(buffer.toList())
        }
    }
}
