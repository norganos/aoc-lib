package de.linkel.aoc.utils.rangeset


interface RangeFactory<T, R, C> where T : Comparable<T>,
                                      R : ClosedRange<T>,
                                      R : Iterable<T>,
                                      C: Number {
    fun build(start: T, endInclusive: T): R
    fun incr(a: T): T
    fun add(a: T, b: T): T
    fun subtract(a: T, b: T): T
    fun count(range: R): C
    fun sumCounts(counts: Iterable<C>): C
    fun without(a: R, b: R): Collection<R>
    fun intersects(a: R, b: R): Boolean
    fun intersect(a: R, b: R): R
    val maxValue: T
    val minValue: T
}
