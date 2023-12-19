package de.linkel.aoc.utils.grid

data class Dimension(
    val width: Int,
    val height: Int
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must not be less than 1")
        if (height <= 0) throw IllegalArgumentException("width must not be less than 1")
    }

    override fun toString(): String {
        return "${width}x$height"
    }
}
