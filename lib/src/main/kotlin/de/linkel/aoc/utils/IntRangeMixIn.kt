package de.linkel.aoc.utils

fun IntRange.intersects(other: IntRange): Boolean {
    return (this.first <= other.first && this.last >= other.first) || (other.first <= this.first && other.last >= this.first)
}

fun IntRange.extend(front: Int = 0, back: Int = 0): IntRange {
    return IntRange(this.first - front, this.last + back)
}
