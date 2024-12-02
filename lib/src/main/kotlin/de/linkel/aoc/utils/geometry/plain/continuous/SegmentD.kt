package de.linkel.aoc.utils.geometry.plain.continuous

class SegmentD(
    val start: PointD,
    val end: PointD,
    override val name: String = ""
): ShapeD<SegmentD> {
    init {
        if (start == end) {
            throw IllegalArgumentException("segment cannot have length zero")
        }
    }

    val vector = end - start

    val length get(): Double = vector.length
    val isVertical get(): Boolean = vector.isVertical
    val isHorizontal get(): Boolean = vector.isHorizontal

    override operator fun contains(point: PointD): Boolean {
        return point == start || point == end || (point - start) in vector
    }

    fun intersectsStrictly(other: SegmentD): Boolean {
        val d1 = vector.determinant(other.start - end)
        val d2 = vector.determinant(other.end - end)
        if ((d1 == 0.0 && contains(other.start)) || (d2 == 0.0 && contains(other.end)))
            return true
        if ((d1 > 0.0 && d2 > 0.0) || (d1 < 0.0 && d2 < 0.0))
            return false
        val d3 = other.vector.determinant(start - other.end)
        val d4 = other.vector.determinant(end - other.end)
        if ((d3 == 0.0 && other.contains(start)) || (d4 == 0.0 && other.contains(end)))
            return true
        if ((d3 > 0.0 && d4 > 0.0) || (d3 < 0.0 && d4 < 0.0))
            return false
        if (d1 == 0.0 && d2 == 0.0 && d3 == 0.0 && d4 == 0.0)
            return false
        return true
    }

    fun overlaps(other: SegmentD): Boolean {
        return vector.parallel(other.vector) && (start in other || end in other || other.start in this || other.end in this)
    }

    override fun intersects(shape: ShapeD<*>): Boolean {
        return when (shape) {
            is PointD -> contains(shape)
            is SegmentD -> intersectsStrictly(shape) || overlaps(shape)
            else -> shape.contains(start) || shape.contains(end) || shape.segments.any { this.intersectsStrictly(it) || this.overlaps(it) }
        }
    }

    fun intersect(other: SegmentD): PointD? {
        val d1 = vector.determinant(other.start - end)
        val d2 = vector.determinant(other.end - end)
        if (d1 == 0.0 && contains(other.start))
            return other.start
        if (d2 == 0.0 && contains(other.end))
            return other.end
        if ((d1 > 0.0 && d2 > 0.0) || (d1 < 0.0 && d2 < 0.0))
            return null
        val d3 = other.vector.determinant(start - other.end)
        val d4 = other.vector.determinant(end - other.end)
        if (d3 == 0.0 && other.contains(start))
            return start
        if (d4 == 0.0 && other.contains(end))
            return end
        if ((d3 > 0.0 && d4 > 0.0) || (d3 < 0.0 && d4 < 0.0))
            return null
        if (d1 == 0.0 && d2 == 0.0 && d3 == 0.0 && d4 == 0.0)
            return null
        val x1 = start.x
        val y1 = start.y
        val x2 = end.x
        val y2 = end.y
        val x3 = other.start.x
        val y3 = other.start.y
        val x4 = other.end.x
        val y4 = other.end.y
        return PointD(
            x = ((x1*y2 - y1*x2)*(x3 - x4) - (x1 - x2)*(x3*y4 - y3*x4)) / ((x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)),
            y = ((x1*y2 - y1*x2)*(y3 - y4) - (y1 - y2)*(x3*y4 - y3*x4)) / ((x1 - x2)*(y3 - y4) - (y1 - y2)*(x3 - x4)),
        )
    }

    override val boundingBox: RectangleD
        get() = RectangleD(start, end)
    override val segments: List<SegmentD>
        get()= listOf(this)
    override val corners: List<PointD>
        get() = listOf(start, end)
    override val area: Double
        get() = 0.0

    override operator fun plus(vector: VectorD): SegmentD {
        return SegmentD(
            start = start + vector,
            end = end + vector,
            name = name
        )
    }
    override operator fun minus(vector: VectorD): SegmentD {
        return SegmentD(
            start = start - vector,
            end = end - vector,
            name = name
        )
    }
    override operator fun times(factor: Double): SegmentD {
        return SegmentD(
            start = start,
            end = start + (vector * factor),
            name = name
        )
    }
    operator fun div(divisor: Double): SegmentD {
        return SegmentD(
            start = start,
            end = start + (vector / divisor),
            name = name
        )
    }

    fun turnAround(): SegmentD = SegmentD(end, start, name)

    override fun toString(): String {
        return "$name $start->$end".trim()
    }

    override fun toAnonymous() = SegmentD(
        start = start,
        end = end
    )
    override fun named(name: String) = SegmentD(
        start = start,
        end = end,
        name = name
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SegmentD

        if (start == other.start && end == other.end) return true
        if (start == other.end && end == other.start) return true

        return false
    }

    override fun hashCode(): Int {
        return start.hashCode() + end.hashCode()
    }
}

fun SegmentD(start: PointD, vector: VectorD, name: String = "") = SegmentD(start, start + vector, name)
