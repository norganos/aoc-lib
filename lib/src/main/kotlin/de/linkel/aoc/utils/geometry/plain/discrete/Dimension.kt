package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.DimensionD

data class Dimension(
    val width: Int,
    val height: Int
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must not be less than 1")
        if (height <= 0) throw IllegalArgumentException("height must not be less than 1")
    }

    override fun toString(): String {
        return "${width}x$height"
    }

    val area get(): Int = width * height

    fun toContinuous() = DimensionD(width.toDouble(), height.toDouble())
}
