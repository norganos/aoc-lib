package de.linkel.aoc.utils.rangeset

import de.linkel.aoc.utils.iterables.intersect
import de.linkel.aoc.utils.iterables.intersects

class IntRangeSetFactory: RangeFactory<Int, IntRange, Int> {
    companion object {
        val INSTANCE = IntRangeSetFactory()
    }

    override fun build(start: Int, endInclusive: Int) = IntRange(start, endInclusive)

    override fun incr(a: Int) = a +1

    override fun add(a: Int, b: Int) = a + b

    override fun subtract(a: Int, b: Int) = a - b

    override fun count(range: IntRange) = range.last - range.first + 1

    override fun sumCounts(counts: Iterable<Int>) = counts.sum()

    override fun without(a: IntRange, b: IntRange): Collection<IntRange> {
        return if (a.first >= b.first && a.last <= b.last) emptyList() // a completely inside b -> nothing left
        else if (a.first < b.first && a.last > b.last) listOf(IntRange(a.first, b.first - 1), IntRange(b.last + 1, a.last)) // b completely inside a -> b cuts a in 2 parts
        else if (a.first < b.first && a.last > b.first) listOf(IntRange(a.first, b.first - 1)) // b cuts a upper end
        else if (a.last > b.last && a.first < b.last) listOf(IntRange(b.last + 1, a.last)) // b cuts a lower end
        else listOf(a) // no intersection -> a stays the same
    }

    override fun intersects(a: IntRange, b: IntRange): Boolean {
        return a.intersects(b)
    }

    override fun intersect(a: IntRange, b: IntRange): IntRange {
        return a.intersect(b)
    }

    override val maxValue = Int.MAX_VALUE
    override val minValue = Int.MIN_VALUE
}

class IntRangeSet(
    ranges: Iterable<IntRange>
): AbstractNumberRangeSet<IntRangeSet, Int, IntRange, Int>(
    ranges,
    IntRangeSetFactory.INSTANCE
) {
    override fun copy(ranges: Iterable<IntRange>) = IntRangeSet(ranges)
}

fun IntRange.toRangeSet(): IntRangeSet {
    return IntRangeSet(listOf(this))
}
