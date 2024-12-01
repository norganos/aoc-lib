package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.max
import kotlin.math.min

data class RectangleD(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double
) {
    init {
        if (width <= 0) throw IllegalArgumentException("width must be greater than 0")
        if (height <= 0) throw IllegalArgumentException("width must be greater than 0")
    }

    var id = ""
    val origin = PointD(x, y)
    val northWest get(): PointD = PointD(x, y)
    @Suppress("unused")
    val northEast get(): PointD = PointD(x + width - 1, y)
    val southEast get(): PointD = PointD(x + width - 1, y + height - 1)
    @Suppress("unused")
    val southWest get(): PointD = PointD(x, y + height - 1)
    val dimension = DimensionD(width, height)

    val corners get(): List<PointD> = listOf(northWest, northEast, southEast, southWest)

    val area get(): Double = width * height

    operator fun contains(point: PointD): Boolean {
        return point.x >= x && point.y >= y && point.x < x + width && point.y < y + height
    }

    fun extendTo(point: PointD): RectangleD {
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

    operator fun plus(vector: VectorD): RectangleD {
        return copy(
            x = x + vector.deltaX,
            y = y + vector.deltaY
        )
    }
    operator fun minus(vector: VectorD): RectangleD {
        return copy(
            x = x - vector.deltaX,
            y = y - vector.deltaY
        )
    }
    operator fun times(factor: Double): RectangleD {
        return copy(
            width = width * factor,
            height = height * factor
        )
    }

    fun intersects(other: RectangleD): Boolean {
        return ((this.x <= other.x && (x + width) >= other.x) || (other.x <= this.x && (other.x + other.width) >= this.x))
            && ((this.y <= other.y && (y + height) >= other.y) || (other.y <= this.y && (other.y + other.height) >= this.y))
    }

    fun intersect(other: RectangleD): RectangleD? {
        val minX = max(x, other.x)
        val minY = max(y, other.y)
        val maxX = min(x + width, other.x + other.width)
        val maxY = min(y + height, other.y + other.height)
        return if (minX <= maxX && minY <= maxY) {
            RectangleD(minX, minY, maxY - minX, maxY - minY)
        } else null
    }

    fun toPolygon(): PolygonD = PolygonD(corners)
}

fun RectangleD(a: PointD, b: PointD) = RectangleD(x = min(a.x, b.x), y = min(a.y, b.y), width = max(a.x, b.x) - min(a.x, b.x) + 1, height = max(a.y, b.y) - min(a.y, b.y) + 1)
fun RectangleD(p: PointD, d: DimensionD) = RectangleD(x = p.x, y = p.y, width = d.width, height = d.height)
