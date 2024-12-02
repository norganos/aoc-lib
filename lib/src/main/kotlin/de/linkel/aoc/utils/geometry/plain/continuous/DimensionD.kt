package de.linkel.aoc.utils.geometry.plain.continuous

data class DimensionD(
    val width: Double,
    val height: Double
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must be greater than zero")
        if (height <= 0) throw IllegalArgumentException("width must be greater than zero")
    }

    override fun toString(): String {
        return "${width}x$height"
    }

    val area get(): Double = width * height
}
