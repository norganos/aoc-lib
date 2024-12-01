package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.PointD

data class Point(
    val x: Int,
    val y: Int
) {
    companion object {
        val ZERO = Point(0, 0)
    }

    operator fun plus(v: Vector): Point {
        return copy(
            x = x + v.deltaX,
            y = y + v.deltaY
        )
    }
    operator fun minus(p: Point): Vector {
        return Vector(
            deltaX = x - p.x,
            deltaY = y - p.y
        )
    }
    operator fun minus(v: Vector): Point {
        return copy(
            x = x - v.deltaX,
            y = y - v.deltaY
        )
    }

    operator fun rangeTo(p: Point): Segment {
        return Segment(this, p)
    }

    override fun toString(): String {
        return "$x/$y"
    }

    fun toContinuous() = PointD(x.toDouble(), y.toDouble())
}
