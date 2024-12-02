package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.PointD

class Point(
    val x: Int,
    val y: Int,
    override val name: String = ""
): Shape<Point> {
    companion object {
        val ZERO = Point(0, 0)
    }

    override val boundingBox: Rectangle
        get() = throw IllegalStateException("no bounding box for point")
    override val segments: List<Segment>
        get() = emptyList()
    override val corners: List<Point>
        get() = listOf(this)
    override val area: Int
        get() = 1

    override operator fun plus(vector: Vector): Point {
        return Point(
            x = x + vector.deltaX,
            y = y + vector.deltaY,
            name = name
        )
    }
    operator fun minus(p: Point): Vector {
        return Vector(
            deltaX = x - p.x,
            deltaY = y - p.y
        )
    }
    override operator fun minus(vector: Vector): Point {
        return Point(
            x = x - vector.deltaX,
            y = y - vector.deltaY,
            name = name
        )
    }

    override fun times(factor: Int): Point {
        return this
    }

    override fun contains(point: Point): Boolean {
        return point == this
    }

    override fun intersects(shape: Shape<*>): Boolean {
        return shape.contains(this)
    }

    operator fun rangeTo(p: Point): Segment {
        return Segment(this, p)
    }

    override fun toString(): String {
        return "$name $x/$y".trim()
    }

    fun toContinuous() = PointD(x.toDouble(), y.toDouble())

    override fun toAnonymous() = Point(
        x = x,
        y = y
    )
    override fun named(name: String) = Point(
        x = x,
        y = y,
        name = name
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Point

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }
}
