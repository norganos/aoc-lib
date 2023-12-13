package de.linkel.aoc.utils.grid

import kotlin.math.abs
import kotlin.math.sign

data class Segment(
    val x: Int,
    val y: Int
) {
    operator fun plus(v: Vector): Segment {
        return copy(
            x = x + v.deltaX,
            y = y + v.deltaY
        )
    }
    operator fun minus(p: Segment): Vector {
        return Vector(
            deltaX = x - p.x,
            deltaY = y - p.y
        )
    }

    operator fun rangeTo(p: Segment): List<Segment> {
        if (p == this) {
            return listOf(p)
        }
        val vector = p - this
        return if (vector.deltaY == 0) {
            (0 .. abs(vector.deltaX))
                .map { i -> this.copy(
                    x = x + (i) * vector.deltaX.sign
                )}
        } else if (vector.deltaX == 0) {
            (0 .. abs(vector.deltaY))
                .map { i -> this.copy(
                    y = y + (i) * vector.deltaY.sign
                )}
        } else {
            throw IllegalArgumentException("rangeTo only works in straight lines")
        }
    }

    override fun toString(): String {
        return "$x/$y"
    }
}
