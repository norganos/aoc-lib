package de.linkel.aoc.utils.grid

import de.linkel.aoc.utils.iterables.intersect
import de.linkel.aoc.utils.iterables.intersects
import kotlin.math.max
import kotlin.math.min

data class Rectangle(
    val x: Int,
    val y: Int,
    val width: Int,
    val height: Int
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must not be less than 1")
        if (height <= 0) throw IllegalArgumentException("width must not be less than 1")
    }

    var id = ""
    val origin = Point(x, y)
    val northWest get(): Point = Point(x, y)
    @Suppress("unused")
    val northEast get(): Point = Point(x + width - 1, y)
    val southEast get(): Point = Point(x + width - 1, y + height - 1)
    @Suppress("unused")
    val southWest get(): Point = Point(x, y + height - 1)
    val dimension = Dimension(width, height)

    val xValues get(): IntRange = x..<(x + width)
    val yValues get(): IntRange = y..<(y + height)
    val corners get(): List<Point> = listOf(northWest, northEast, southEast, southWest)

    val points get(): Sequence<Point> = sequence {
        yValues.forEach { y ->
            xValues.forEach { x ->
                yield(Point(x, y))
            }
        }
    }

    val area get(): Int = width * height

    operator fun contains(point: Point): Boolean {
        return point.x >= x && point.y >= y && point.x < x + width && point.y < y + height
    }

    fun extendTo(point: Point): Rectangle {
        val x = min(this.x, point.x)
        val y = min(this.y, point.y)
        val w = max(this.x + width, point.x + 1) - x
        val h = max(this.y + height, point.y + 1) - y
        return copy(
            x = x,
            y = y,
            width = w,
            height = h
        )
    }

    override fun toString(): String {
        return "$id ${width}x${height}@${x}/${y}".trim()
    }

    operator fun plus(vector: Vector): Rectangle {
        return copy(
            x = x + vector.deltaX,
            y = y + vector.deltaY
        )
    }
    operator fun minus(vector: Vector): Rectangle {
        return copy(
            x = x - vector.deltaX,
            y = y - vector.deltaY
        )
    }
    operator fun times(factor: Int): Rectangle {
        return copy(
            width = width * factor,
            height = height * factor
        )
    }

    fun intersects(other: Rectangle): Boolean {
        return xValues.intersects(other.xValues) && yValues.intersects(other.yValues)
    }

    fun intersect(other: Rectangle): Rectangle? {
        val xx = xValues.intersect(other.xValues)
        val yy = yValues.intersect(other.yValues)
        return if (xx.isEmpty() || yy.isEmpty()) null
        else Rectangle(xx, yy)
    }

    fun toPolygon(): Polygon = Polygon(corners)
}

fun Rectangle(x: IntRange, y: IntRange) = Rectangle(x = x.first, y = y.first, width = x.last - x.first + 1, height = y.last - y.first + 1)
fun Rectangle(a: Point, b: Point) = Rectangle(x = min(a.x, b.x), y = min(a.y, b.y), width = max(a.x, b.x) - min(a.x, b.x) + 1, height = max(a.y, b.y) - min(a.y, b.y) + 1)
fun Rectangle(p: Point, d: Dimension) = Rectangle(x = p.x, y = p.y, width = d.width, height = d.height)
