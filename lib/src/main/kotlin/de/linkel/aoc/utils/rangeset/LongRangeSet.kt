package de.linkel.aoc.utils.rangeset

import de.linkel.aoc.utils.iterables.intersect
import de.linkel.aoc.utils.iterables.intersects

class LongRangeSetFactory: RangeFactory<Long, LongRange, Long> {
    companion object {
        val INSTANCE = LongRangeSetFactory()
    }

    override fun build(start: Long, endInclusive: Long) = LongRange(start, endInclusive)

    override fun incr(a: Long) = a +1

    override fun add(a: Long, b: Long) = a + b

    override fun subtract(a: Long, b: Long) = a - b

    override fun count(range: LongRange) = range.last - range.first + 1

    override fun sumCounts(counts: Iterable<Long>) = counts.sum()

    override fun without(a: LongRange, b: LongRange): Collection<LongRange> {
        return if (a.first >= b.first && a.last <= b.last) emptyList() // a completely inside b -> nothing left
        else if (a.first < b.first && a.last > b.last) listOf(LongRange(a.first, b.first - 1), LongRange(b.last + 1, a.last)) // b completely inside a -> b cuts a in 2 parts
        else if (a.first < b.first && a.last > b.first) listOf(LongRange(a.first, b.first - 1)) // b cuts a upper end
        else if (a.last > b.last && a.first < b.last) listOf(LongRange(b.last + 1, a.last)) // b cuts a lower end
        else listOf(a) // no intersection -> a stays the same
    }

    override fun intersects(a: LongRange, b: LongRange): Boolean {
        return a.intersects(b)
    }

    override fun intersect(a: LongRange, b: LongRange): LongRange {
        return a.intersect(b)
    }

    override val maxValue = Long.MAX_VALUE
    override val minValue = Long.MIN_VALUE
}

class LongRangeSet(
    ranges: Iterable<LongRange>
): AbstractNumberRangeSet<LongRangeSet, Long, LongRange, Long>(
    ranges,
    LongRangeSetFactory.INSTANCE
) {
    override fun copy(ranges: Iterable<LongRange>) = LongRangeSet(ranges)
}

fun LongRange.toRangeSet(): LongRangeSet {
    return LongRangeSet(listOf(this))
}
