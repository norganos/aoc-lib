package de.linkel.aoc.lib.grid

data class Dimension(
    val width: Int,
    val height: Int
) {

    override fun toString(): String {
        return "${width}x$height"
    }
}
