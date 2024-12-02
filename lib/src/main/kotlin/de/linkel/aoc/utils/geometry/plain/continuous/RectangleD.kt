package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.max
import kotlin.math.min

class RectangleD(
    val x: Double,
    val y: Double,
    val width: Double,
    val height: Double,
    override val name: String = "",
): ShapeD<RectangleD> {
    init {
        if (width <= 0) throw IllegalArgumentException("width must be greater than 0")
        if (height <= 0) throw IllegalArgumentException("width must be greater than 0")
    }

    val origin = PointD(x, y)
    val northWest get(): PointD = PointD(x, y)
    val northEast get(): PointD = PointD(x + width - 1, y)
    val southEast get(): PointD = PointD(x + width - 1, y + height - 1)
    val southWest get(): PointD = PointD(x, y + height - 1)
    val dimension = DimensionD(width, height)

    override val boundingBox get(): RectangleD = this
    override val segments get(): List<SegmentD> = listOf(
        SegmentD(northWest, VectorD(width, 0.0)),
        SegmentD(northEast, VectorD(0.0, height)),
        SegmentD(southEast, VectorD(-width, 0.0)),
        SegmentD(southWest, VectorD(0.0, -height))
    )

    override val corners get(): List<PointD> = listOf(northWest, northEast, southEast, southWest)

    override val area get(): Double = width * height

    override operator fun contains(point: PointD): Boolean {
        return point.x >= x && point.y >= y && point.x < x + width && point.y < y + height
    }
    operator fun contains(shape: ShapeD<*>): Boolean {
        return if (shape is RectangleD)
            x <= shape.x && y <= shape.y && x + width >= shape.x + shape.width && y + height >= shape.y + shape.height
        else
            contains(shape.boundingBox)
    }

    fun extendTo(point: PointD): RectangleD {
        val x = min(this.x, point.x)
        val y = min(this.y, point.y)
        val w = max(this.x + width, point.x + 1) - x
        val h = max(this.y + height, point.y + 1) - y
        return RectangleD(
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

    override operator fun plus(vector: VectorD): RectangleD {
        return RectangleD(
            x = x + vector.deltaX,
            y = y + vector.deltaY,
            width = width,
            height = height,
            name = name
        )
    }
    override operator fun minus(vector: VectorD): RectangleD {
        return RectangleD(
            x = x - vector.deltaX,
            y = y - vector.deltaY,
            width = width,
            height = height,
            name = name
        )
    }
    override operator fun times(factor: Double): RectangleD {
        return RectangleD(
            x = x,
            y = y,
            width = width * factor,
            height = height * factor,
            name = name
        )
    }

    override fun intersects(shape: ShapeD<*>): Boolean {
        return when (shape) {
            is PointD -> contains(shape)
            is SegmentD -> x < max(shape.start.x, shape.end.x) && x + width > min(shape.start.x, shape.end.x)
                    && y < max(shape.start.y, shape.end.y) && y + height > min(shape.start.y, shape.end.y)
            is RectangleD -> x < shape.x + shape.width && x + width > shape.x
                    && y < shape.y + shape.height && y + height > shape.y
            else -> shape.segments.any { intersects(it) } || shape.contains(origin)
        }
    }

    fun intersect(other: RectangleD): RectangleD? {
        val left = max(x, other.x)
        val top = max(y, other.y)
        val right = min(x + width, other.x + other.width)
        val bottom = min(y + height, other.y + other.height)
        return if (left < right && top < bottom) {
            RectangleD(left, top, right - left, bottom - top)
        } else null
    }

    fun toPolygon(): PolygonD = PolygonD(corners, name)

    override fun toAnonymous() = RectangleD(x, y, width, height)
    override fun named(name: String) = RectangleD(x, y, width, height, name)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as RectangleD

        if (x != other.x) return false
        if (y != other.y) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        result = 31 * result + width.hashCode()
        result = 31 * result + height.hashCode()
        return result
    }
}

fun RectangleD(a: PointD, b: PointD, name: String = "") = RectangleD(x = min(a.x, b.x), y = min(a.y, b.y), width = max(a.x, b.x) - min(a.x, b.x) + 1, height = max(a.y, b.y) - min(a.y, b.y) + 1, name = name)
fun RectangleD(origin: PointD, dimension: DimensionD, name: String = "") = RectangleD(x = origin.x, y = origin.y, width = dimension.width, height = dimension.height, name = name)
