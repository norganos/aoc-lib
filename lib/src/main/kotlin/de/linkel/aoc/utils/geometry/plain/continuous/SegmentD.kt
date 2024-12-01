package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.max
import kotlin.math.min

data class SegmentD(
    val start: PointD,
    val end: PointD
) {
    init {
        if (start == end) {
            throw IllegalArgumentException("segment cannot have length zero")
        }
    }

    val vector = end - start

    val length get(): Double = vector.length

    operator fun contains(point: PointD): Boolean {
        return point == start || point == end || (point - start) in vector
    }

    operator fun plus(v: VectorD): SegmentD {
        return SegmentD(
            start = start + v,
            end = end + v
        )
    }
    operator fun minus(v: VectorD): SegmentD {
        return SegmentD(
            start = start - v,
            end = end - v
        )
    }
    operator fun times(factor: Double): SegmentD {
        return copy(
            start = start,
            end = start + (vector * factor)
        )
    }
    operator fun div(divisor: Double): SegmentD {
        return copy(
            start = start,
            end = start + (vector / divisor)
        )
    }

    private fun angleDirection(p: PointD, q: PointD, r: PointD) = (q - p).determinant(r - q)
    private fun onSegment(p: PointD, q: PointD, x: PointD): Boolean {
        val topright = max(p.x, q.x) to min(p.y, q.y)
        val botleft = min(p.x, q.x) to max(p.y, q.y)
        return x.x <= topright.first && x.y <= topright.second && botleft.first <= x.x && botleft.second <= x.y
    }

    fun intersects(other: SegmentD): Boolean {
        val d1 = angleDirection(start, end, other.start)
        val d2 = angleDirection(start, end, other.end)
        if ((d1 == 0.0 && onSegment(start, end, other.start)) || (d2 == 0.0 && onSegment(start, end, other.end)))
            return true
        if ((d1 > 0.0 && d2 > 0.0) || (d1 < 0.0 && d2 < 0.0))
            return false
        val d3 = angleDirection(other.start, other.end, start)
        val d4 = angleDirection(other.start, other.end, end)
        if ((d3 == 0.0 && onSegment(other.start, other.end, start)) || (d4 == 0.0 && onSegment(other.start, other.end, end)))
            return true
        if ((d3 > 0.0 && d4 > 0.0) || (d3 < 0.0 && d4 < 0.0))
            return false
        if (d1 == 0.0 && d2 == 0.0 && d3 == 0.0 && d4 == 0.0)
            return false
        return true
    }

    override fun toString(): String {
        return "$start->$end"
    }
}

fun SegmentD(start: PointD, vector: VectorD) = SegmentD(start, start + vector)
