package de.linkel.aoc.utils.iterables


class StoppingIterator<T>(
    private val parent: Iterator<T>,
    private val endPredicate: (T) -> Boolean
): Iterator<T> {
    private var buffer: T? = null
    private var consumed: Boolean = true

    init {
        if (parent.hasNext()) {
            buffer = parent.next()
            if (!endPredicate(buffer!!)) {
                consumed = false
            }
        }
    }

    override fun hasNext(): Boolean {
        return !consumed && !endPredicate(buffer!!)
    }

    override fun next(): T {
        if (hasNext()) {
            val result = buffer!!
            if (parent.hasNext()) {
                buffer = parent.next()
            } else {
                consumed = true
            }
            return result
        } else throw NoSuchElementException()
    }
}
class IteratorSequence<T>(
    private val iterator: Iterator<T>
): Sequence<T> {
    private var consumed = false
    override fun iterator(): Iterator<T> {
        if (consumed) throw NoSuchElementException()
        consumed = true
        return iterator
    }
}

fun <A,B,T> Sequence<T>.inTwoBlocks(delimiterPredicate: (T) -> Boolean, block1: (Sequence<T>) -> A, block2: (buffer: A, Sequence<T>) -> B): B {
    val iterator = this.iterator()

    val first = block1(IteratorSequence(StoppingIterator(iterator, delimiterPredicate)))
    return block2(first, IteratorSequence(iterator))
}
fun <A,B,T> Iterable<T>.inTwoBlocks(delimiterPredicate: (T) -> Boolean, block1: (Sequence<T>) -> A, block2: (buffer: A, Sequence<T>) -> B): B {
    val iterator = this.iterator()

    val first = block1(IteratorSequence(StoppingIterator(iterator, delimiterPredicate)))
    return block2(first, IteratorSequence(iterator))
}