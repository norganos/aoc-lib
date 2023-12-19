package de.linkel.aoc.utils.grid

import de.linkel.aoc.utils.CommonMath
import de.linkel.aoc.utils.Geometry
import kotlin.math.abs
import kotlin.math.sign

data class Segment(
    val start: Point,
    val end: Point
): Iterable<Point> {
    init {
        if (start == end) {
            throw IllegalArgumentException("segment cannot have length zero")
        }
    }

    val vector = end - start

    val manhattanDistance get(): Int = vector.manhattenDistance

    // number of points on the segment
    val size get(): Int {
        return if (vector.deltaY == 0 || vector.deltaX == 0)
            vector.manhattenDistance + 1
        else
            CommonMath.gcd(vector.deltaX, vector.deltaY) + 1
    }

    operator fun contains(point: Point): Boolean {
        return point == start || point == end || (point - start) in vector
    }

    operator fun plus(v: Vector): Segment {
        return Segment(
            start = start + v,
            end = end + v
        )
    }
    operator fun minus(v: Vector): Segment {
        return Segment(
            start = start - v,
            end = end - v
        )
    }
    operator fun times(factor: Int): Segment {
        return copy(
            start = start,
            end = start + (vector * factor)
        )
    }
    operator fun div(divisor: Int): Segment {
        return copy(
            start = start,
            end = start + (vector / divisor)
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



    //TODO: kriegen wir easy nen schnitt hin?
    fun intersects(other: Segment): Boolean {
        return Geometry.intersect(
            start.x.toFloat()  to start.y.toFloat() ,
            end.x.toFloat()  to end.y.toFloat() ,
            other.start.x.toFloat()  to other.start.y.toFloat() ,
            other.end.x.toFloat()  to other.end.y.toFloat()
        )
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
}

fun Segment(start: Point, vector: Vector) = Segment(start, start + vector)
