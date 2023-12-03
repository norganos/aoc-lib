package de.linkel.aoc.utils

fun <T> Sequence<T>.prepend(element: T): Sequence<T> {
    return if (this is ConcatSequence<T>) {
        ConcatSequence(listOf(sequenceOf(element)) + this.sequences)
    } else {
        ConcatSequence(listOf(sequenceOf(element), this))
    }
}
fun <T> Sequence<T>.prepend(other: Sequence<T>): Sequence<T> {
    return if (this is ConcatSequence<T>) {
        ConcatSequence(listOf(other) + this.sequences)
    } else {
        ConcatSequence(listOf(other, this))
    }
}
fun <T> Sequence<T>.append(element: T): Sequence<T> {
    return if (this is ConcatSequence<T>) {
        ConcatSequence(this.sequences + listOf(sequenceOf(element)))
    } else {
        ConcatSequence(listOf(this, sequenceOf(element)))
    }
}
fun <T> Sequence<T>.append(other: Sequence<T>): Sequence<T> {
    return if (this is ConcatSequence<T>) {
        ConcatSequence(this.sequences + listOf(other))
    } else {
        ConcatSequence(listOf(this, other))
    }
}
operator fun <T> Sequence<T>.plus(other: Sequence<T>): Sequence<T> {
    return if (this is ConcatSequence<T>) {
        ConcatSequence(this.sequences + listOf(other))
    } else {
        ConcatSequence(listOf(this, other))
    }
}

class ConcatSequence<T>(
    val sequences: List<Sequence<T>>
): Sequence<T> {
    override fun iterator(): Iterator<T> {
        return ConcatIterator(sequences.map { it.iterator() })
    }
}

class ConcatIterator<T>(
    iterators: List<Iterator<T>>
): Iterator<T> {
    private val parents = iterators.toMutableList()
    private var current: Iterator<T>? = null

    private fun cycleIfNecessary() {
        while (current?.hasNext() != true && parents.isNotEmpty()) {
            current = parents.removeAt(0)
        }
    }

    override fun hasNext(): Boolean {
        cycleIfNecessary()
        return current?.hasNext() == true
    }

    override fun next(): T {
        cycleIfNecessary()
        return current?.next() ?: throw NoSuchElementException()
    }
}
