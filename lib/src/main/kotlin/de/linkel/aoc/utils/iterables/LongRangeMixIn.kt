package de.linkel.aoc.utils.iterables

fun LongRange.intersects(other: LongRange): Boolean {
    return (this.first <= other.first && this.last >= other.first) || (other.first <= this.first && other.last >= this.first)
}

fun LongRange.intersect(other: LongRange): LongRange {
    return LongRange(kotlin.math.max(this.first, other.first), kotlin.math.min(this.last, other.last))
}

fun LongRange.extend(front: Long = 0, back: Long = 0): LongRange {
    return LongRange(this.first - front, this.last + back)
}

fun LongRange.move(offset: Long): LongRange {
    return LongRange(this.first + offset, this.last + offset)
}
