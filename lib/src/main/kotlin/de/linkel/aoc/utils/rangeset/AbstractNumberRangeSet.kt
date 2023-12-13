package de.linkel.aoc.utils.rangeset

import de.linkel.aoc.utils.iterables.ConcatIterator

abstract class AbstractNumberRangeSet<O, T, R, C>(
    ranges: Iterable<R>,
    private val factory: RangeFactory<T, R, C>
): Iterable<T> where O : AbstractNumberRangeSet<O, T, R, C>,
                     T : Comparable<T>,
                     R : ClosedRange<T>,
                     R : Iterable<T>,
                     C: Number {
    val segments: List<R>
    val first: T
    val last: T
    val size: C

    init {
        val cache = mutableListOf<R>()
        ranges.filter { !it.isEmpty() }.sortedBy { it.start }.forEach { range ->
            if (cache.isNotEmpty() && (cache.last().endInclusive >= range.start || factory.incr(cache.last().endInclusive) == range.start)) {
                val last = cache.last()
                cache[cache.size - 1] = factory.build(last.start, if (last.endInclusive > range.endInclusive) last.endInclusive else range.endInclusive)
            } else {
                cache.add(range)
            }
        }
        segments = cache.toList()
        first = segments.firstOrNull()?.start ?: factory.maxValue
        last = segments.lastOrNull()?.endInclusive ?: factory.minValue
        size = factory.sumCounts(segments.map { factory.count(it) })
    }

    fun min(): T = if (isNotEmpty()) first else throw NoSuchElementException()
    fun max(): T = if (isNotEmpty()) last else throw NoSuchElementException()

    fun isEmpty() = segments.isEmpty()
    fun isNotEmpty() = segments.isNotEmpty()
    fun contains(value: T) = segments.any { it.contains(value) }
    fun contains(value: R) = segments.any { it.contains(value.start) && it.contains(value.endInclusive) }
    fun intersects(value: R) = segments.any { factory.intersects(it, value) }

    override fun iterator(): Iterator<T> {
        return ConcatIterator(segments.map { it.iterator() })
    }

    protected abstract fun copy(ranges: Iterable<R>): O

    operator fun minus(range: R): O {
        return copy(
            segments
                .flatMap { factory.without(it, range) }
        )
    }

    operator fun minus(other: O): O {
        return copy(
            other.segments.fold(segments) { ranges, sub ->
                ranges.flatMap {
                    factory.without(it, sub)
                }
            }
        )
    }

    operator fun plus(range: R): O {
        return copy(
            segments + listOf(range)
        )
    }

    operator fun plus(other: O): O {
        return copy(
            segments + other.segments
        )
    }

    fun intersect(range: R): O {
        return copy(
            segments
                .map { factory.intersect(it, range) }
        )
    }

    fun intersect(other: O): O {
        return copy(
            segments
                .flatMap { range ->
                    other.segments
                        .map { factory.intersect(range, it) }
                }
        )
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as AbstractNumberRangeSet<*, *, *, *>

        if (factory != other.factory) return false
        if (segments != other.segments) return false
        if (first != other.first) return false
        if (last != other.last) return false
        if (size != other.size) return false

        return true
    }

    override fun hashCode(): Int {
        var result = factory.hashCode()
        result = 31 * result + segments.hashCode()
        result = 31 * result + first.hashCode()
        result = 31 * result + last.hashCode()
        result = 31 * result + size.hashCode()
        return result
    }

    override fun toString(): String {
        return segments.joinToString(",").ifEmpty { "empty" }
    }
}
