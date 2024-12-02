package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.SegmentD
import de.linkel.aoc.utils.math.CommonMath
import kotlin.math.abs
import kotlin.math.sign

class Segment(
    val start: Point,
    val end: Point,
    override val name: String = ""
): Iterable<Point>, Shape<Segment> {
    init {
        if (start == end) {
            throw IllegalArgumentException("segment cannot have length zero")
        }
    }

    val vector = end - start

    val manhattanDistance get(): Int = vector.manhattanDistance
    val length get(): Double = vector.length
    val isVertical get(): Boolean = vector.isVertical
    val isHorizontal get(): Boolean = vector.isHorizontal

    // number of points on the segment
    val size get(): Int {
        return if (vector.deltaY == 0 || vector.deltaX == 0)
            vector.manhattanDistance + 1
        else
            CommonMath.gcd(vector.deltaX, vector.deltaY) + 1
    }

    override operator fun contains(point: Point): Boolean {
        return point == start || point == end || (point - start) in vector
    }

    override val boundingBox: Rectangle
        get() = Rectangle(start, end)
    override val segments: List<Segment>
        get()= listOf(this)
    override val corners: List<Point>
        get() = listOf(start, end)
    override val area: Int
        get() = count()

    override operator fun plus(vector: Vector): Segment {
        return Segment(
            start = start + vector,
            end = end + vector,
            name = name
        )
    }
    override operator fun minus(vector: Vector): Segment {
        return Segment(
            start = start - vector,
            end = end - vector,
            name = name
        )
    }
    override operator fun times(factor: Int): Segment {
        return Segment(
            start = start,
            end = start + (vector * factor),
            name = name
        )
    }
    operator fun div(divisor: Int): Segment {
        return Segment(
            start = start,
            end = start + (vector / divisor),
            name = name
        )
    }

    override fun iterator(): Iterator<Point> {
        return if (vector.deltaY == 0) {
            (0 .. abs(vector.deltaX))
                .map { i -> Point(
                    x = start.x + (i) * vector.deltaX.sign,
                    y = start.y
                )}
                .iterator()
        } else if (vector.deltaX == 0) {
            (0 .. abs(vector.deltaY))
                .map { i -> Point(
                    x = start.x,
                    y = start.y + (i) * vector.deltaY.sign
                )}
                .iterator()
        } else {
            val gcd = CommonMath.gcd(vector.deltaX, vector.deltaY)
            val unit = vector / gcd
            (0..gcd)
                .map { i -> start + (unit * i) }
                .iterator()
        }
    }

    fun intersectsStrictly(other: Segment): Boolean {
        return toContinuous().intersectsStrictly(other.toContinuous())
    }

    fun overlaps(other: Segment): Boolean {
        return vector.parallel(other.vector) && (start in other || end in other || other.start in this || other.end in this)
    }

    override fun intersects(shape: Shape<*>): Boolean {
        return when (shape) {
            is Point -> contains(shape)
            is Segment -> intersectsStrictly(shape) || overlaps(shape)
            else -> shape.contains(start) || shape.contains(end) || shape.segments.any { this.intersectsStrictly(it) || this.overlaps(it) }
        }
    }

    fun intersect(other: Segment): Point? {
        val intersection = toSet().intersect(other.toSet())
        return if (intersection.size == 1)
            intersection.first()
        else null
    }

    override fun toString(): String {
        return "$name $start->$end".trim()
    }

    fun toContinuous() = SegmentD(start.toContinuous(), end.toContinuous())

    override fun toAnonymous() = Segment(
        start = start,
        end = end
    )
    override fun named(name: String) = Segment(
        start = start,
        end = end,
        name = name
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Segment

        if (start == other.start && end == other.end) return true
        if (start == other.end && end == other.start) return true

        return false
    }

    override fun hashCode(): Int {
        return start.hashCode() + end.hashCode()
    }
}

fun Segment(start: Point, vector: Vector, name: String = "") = Segment(start, start + vector, name)
