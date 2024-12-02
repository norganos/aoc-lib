package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.RectangleD
import kotlin.math.max
import kotlin.math.min

class Rectangle(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int,
    override val name: String = "",
): Shape<Rectangle> {
    init {
        if (width <= 0) throw IllegalArgumentException("width must not be less than 1")
        if (height <= 0) throw IllegalArgumentException("width must not be less than 1")
    }

    val origin = Point(x, y)
    val northWest get(): Point = Point(x, y)
    val northEast get(): Point = Point(x + width - 1, y)
    val southEast get(): Point = Point(x + width - 1, y + height - 1)
    val southWest get(): Point = Point(x, y + height - 1)
    val dimension = Dimension(width, height)

    val xValues get(): IntRange = x..<(x + width)
    val yValues get(): IntRange = y..<(y + height)
    override val corners get(): List<Point> = listOf(northWest, northEast, southEast, southWest)
    override val segments get(): List<Segment> = listOf(
        Segment(northWest, Vector(width, 0)),
        Segment(northEast, Vector(0, height)),
        Segment(southEast, Vector(-width, 0)),
        Segment(southWest, Vector(0, -height))
    )

    val points get(): Sequence<Point> = sequence {
        yValues.forEach { y ->
            xValues.forEach { x ->
                yield(Point(x, y))
            }
        }
    }

    override val area get(): Int = width * height
    override val boundingBox get(): Rectangle = this

    override operator fun contains(point: Point): Boolean {
        return point.x >= x && point.y >= y && point.x < x + width && point.y < y + height
    }
    operator fun contains(shape: Shape<*>): Boolean {
        return if (shape is Rectangle)
            x <= shape.x && y <= shape.y && x + width >= shape.x + shape.width && y + height >= shape.y + shape.height
        else
            contains(shape.boundingBox)
    }

    fun extendTo(point: Point): Rectangle {
        val x = min(this.x, point.x)
        val y = min(this.y, point.y)
        val w = max(this.x + width, point.x + 1) - x
        val h = max(this.y + height, point.y + 1) - y
        return Rectangle(
            x = x,
            y = y,
            width = w,
            height = h,
            name = name
        )
    }

    override fun toString(): String {
        return "$name ${width}x${height}@${x}/${y}".trim()
    }

    override operator fun plus(vector: Vector): Rectangle {
        return Rectangle(
            x = x + vector.deltaX,
            y = y + vector.deltaY,
            width = width,
            height = height,
            name = name
        )
    }
    override operator fun minus(vector: Vector): Rectangle {
        return Rectangle(
            x = x - vector.deltaX,
            y = y - vector.deltaY,
            width = width,
            height = height,
            name = name
        )
    }
    override operator fun times(factor: Int): Rectangle {
        return Rectangle(
            x = x,
            y = y,
            width = width * factor,
            height = height * factor,
            name = name
        )
    }

    override fun intersects(shape: Shape<*>): Boolean {
        return when (shape) {
            is Point -> contains(shape)
            is Segment -> x < max(shape.start.x, shape.end.x) && x + width > min(shape.start.x, shape.end.x)
                    && y < max(shape.start.y, shape.end.y) && y + height > min(shape.start.y, shape.end.y)
            is Rectangle -> x < shape.x + shape.width && x + width > shape.x
                    && y < shape.y + shape.height && y + height > shape.y
            else -> shape.segments.any { intersects(it) } || shape.contains(origin)
        }
    }

    fun intersect(other: Rectangle): Rectangle? {
        val left = max(x, other.x)
        val top = max(y, other.y)
        val right = min(x + width, other.x + other.width)
        val bottom = min(y + height, other.y + other.height)
        return if (left <= right && top <= bottom) {
            Rectangle(left, top, right - left, bottom - top)
        } else null
    }

    fun toPolygon(): Polygon = Polygon(corners, name)

    fun toContinuous() = RectangleD(x.toDouble(), y.toDouble(), width.toDouble(), height.toDouble())

    override fun toAnonymous() = Rectangle(x, y, width, height)
    override fun named(name: String) = Rectangle(x, y, width, height, name)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rectangle

        if (x != other.x) return false
        if (y != other.y) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}

fun Rectangle(a: Point, b: Point, name: String = "") = Rectangle(x = min(a.x, b.x), y = min(a.y, b.y), width = max(a.x, b.x) - min(a.x, b.x) + 1, height = max(a.y, b.y) - min(a.y, b.y) + 1, name = name)
fun Rectangle(origin: Point, dimension: Dimension, name: String = "") = Rectangle(x = origin.x, y = origin.y, width = dimension.width, height = dimension.height, name = name)
