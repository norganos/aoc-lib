package de.linkel.aoc.utils.geometry.plain.continuous

data class DimensionD(
    val width: Double,
    val height: Double
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must not be less than 1")
        if (height <= 0) throw IllegalArgumentException("width must not be less than 1")
    }

    override fun toString(): String {
        return "${width}x$height"
    }
}
