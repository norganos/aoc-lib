package de.linkel.aoc.utils.mixins

fun IntRange.intersects(other: IntRange): Boolean {
    return (this.first <= other.first && this.last >= other.first) || (other.first <= this.first && other.last >= this.first)
}

fun IntRange.intersect(other: IntRange): IntRange {
    return if (this.intersects(other)) IntRange(kotlin.math.max(this.first, other.first), kotlin.math.min(this.last, other.last))
        else IntRange.EMPTY
}

fun IntRange.extend(front: Int = 0, back: Int = 0): IntRange {
    return IntRange(this.first - front, this.last + back)
}

fun IntRange.move(offset: Int): IntRange {
    return IntRange(this.first + offset, this.last + offset)
}
