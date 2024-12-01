package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.SegmentD
import de.linkel.aoc.utils.math.CommonMath
import de.linkel.aoc.utils.math.Geometry
import kotlin.math.abs
import kotlin.math.sign

class Segment(
    val start: Point,
    val end: Point,
    val name: String = ""
): Iterable<Point> {
    init {
        if (start == end) {
            throw IllegalArgumentException("segment cannot have length zero")
        }
    }

    val vector = end - start

    val manhattanDistance get(): Int = vector.manhattanDistance
    val distance get(): Double = vector.distance

    // number of points on the segment
    val size get(): Int {
        return if (vector.deltaY == 0 || vector.deltaX == 0)
            vector.manhattanDistance + 1
        else
            CommonMath.gcd(vector.deltaX, vector.deltaY) + 1
    }

    operator fun contains(point: Point): Boolean {
        return point == start || point == end || (point - start) in vector
    }

    operator fun plus(v: Vector): Segment {
        return Segment(
            start = start + v,
            end = end + v,
            name = name
        )
    }
    operator fun minus(v: Vector): Segment {
        return Segment(
            start = start - v,
            end = end - v,
            name = name
        )
    }
    operator fun times(factor: Int): Segment {
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
                .map { i -> start.copy(
                    x = start.x + (i) * vector.deltaX.sign
                )}
                .iterator()
        } else if (vector.deltaX == 0) {
            (0 .. abs(vector.deltaY))
                .map { i -> start.copy(
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

    fun intersects(other: Segment): Boolean {
        return Geometry.intersect(
            start.x.toFloat() to start.y.toFloat(),
            end.x.toFloat() to end.y.toFloat(),
            other.start.x.toFloat() to other.start.y.toFloat(),
            other.end.x.toFloat() to other.end.y.toFloat()
        ) || (vector.parallel(other.vector) && (start in other || end in other || other.start in this || other.end in this))
    }

    fun intersect(other: Segment): Point? {
        val intersection = toSet().intersect(other.toSet())
        return if (intersection.size == 1)
            intersection.first()
        else null
    }

    override fun toString(): String {
        return "$start->$end"
    }

    fun toContinuous() = SegmentD(start.toContinuous(), end.toContinuous())

    fun toAnonymous() = Segment(
        start = start,
        end = end
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
