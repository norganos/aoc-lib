package de.linkel.aoc.lib.grid

data class DataPoint<T>(
    val point: Point,
    val data: T
) {
    override fun toString(): String {
        return "$point -> $data"
    }
}
