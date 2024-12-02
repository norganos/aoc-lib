package de.linkel.aoc.utils.geometry.plain.continuous

import de.linkel.aoc.utils.iterables.contains
import de.linkel.aoc.utils.iterables.repeat
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class PolygonD(
    corners: List<PointD>,
    override val name: String = "",
): ShapeD<PolygonD> {
    override val corners: List<PointD>
    override val boundingBox: RectangleD

    init {
        val set = corners.toSet()
        if (set.size < 3) {
            throw IllegalArgumentException("polygon needs at least 3 distinct points")
        }
        if (set.zipWithNext().map { (a, b) -> a - b }.zipWithNext().none { (a, b) -> a.determinant(b) != 0.0 }) {
            throw IllegalArgumentException("can't build polygon if all points are on one line")
        }

        //TODO: can we check if the polygon is well-formed?
        // i.e. does not have a edge the goes through the polygon itself
        // i.e. all edges are part of the perimeter
        if (corners.last() != corners.first()) {
            this.corners = corners + corners.first()
        } else {
            this.corners = corners
        }

        var minX = Double.MAX_VALUE
        var minY = Double.MAX_VALUE
        var maxX = Double.MIN_VALUE
        var maxY = Double.MIN_VALUE
        corners.forEach {
            minX = min(it.x, minX)
            maxX = max(it.x, maxX)
            minY = min(it.y, minY)
            maxY = max(it.y, maxY)
        }
        boundingBox = RectangleD(minX, minY, maxX - minX, maxY - minY)
    }

    override val segments get(): List<SegmentD> = corners.zipWithNext().map { (a, b) -> SegmentD(a, b - a) }

    override val area get(): Double {
        return shoelaceFormula(corners)
    }

    private fun shoelaceFormula(corners: List<PointD>): Double =
        corners.zipWithNext().sumOf { (a, b) -> a.x * b.y - a.y * b.x } / 2

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PolygonD

        if (corners.size != other.corners.size) {
            return false
        }
        val myCornersDeDup = corners.take(corners.size-1)
        val otherCornersDeDup = other.corners.take(corners.size-1)
        if (myCornersDeDup.toSet() != otherCornersDeDup.toSet()) {
            return false
        }
        if (myCornersDeDup in otherCornersDeDup.repeat(2).toList()) {
            return true
        }
        if (myCornersDeDup in otherCornersDeDup.reversed().repeat(2).toList()) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        // hier bewusst set-ähnliche implementierung, so dass Rotation der Punkte denselben Hash ergibt
        return corners.take(corners.size-1).sumOf { it.hashCode() }
    }

    override fun contains(point: PointD): Boolean {
        return boundingBox.contains(point) && (
                corners
                    .zipWithNext()
                    .filter { (a, b) -> (a.y > point.y) != (b.y > point.y) }
                    .count { (a, b) -> point.x < (a.x + (b.x - a.x) / (b.y - a.y) * (point.y - a.y)) }
                    .isOdd()
                        || segments.any { point in it }
                )
    }
    private fun Int.isOdd() = this % 2 != 0

    override fun plus(vector: VectorD): PolygonD {
        return PolygonD(corners.map { it + vector }, name)
    }

    override fun minus(vector: VectorD): PolygonD {
        return PolygonD(corners.map { it + vector }, name)
    }

    override fun times(factor: Double): PolygonD {
        throw NotImplementedError("not yet implemented")
    }

    override fun intersects(shape: ShapeD<*>): Boolean {
        return when (shape) {
            is PointD -> contains(shape)
            is SegmentD -> contains(shape.start) || contains(shape.end) || segments.any { polygonSegment ->
                polygonSegment.intersects(
                    shape
                )
            }

            else -> boundingBox.intersects(shape.boundingBox) && shape.segments.any { this.intersects(it) }
        }
    }

    override fun toAnonymous() = PolygonD(corners)
    override fun named(name: String) = PolygonD(corners, name)

    fun isConcave(): Boolean {
        return (segments.plusElement(segments.first()))
            .zipWithNext { a, b -> a.vector.determinant(b.vector) }
            .filter { it != 0.0 }
            .map { it.sign }
            .distinct()
            .size == 1
    }

    fun isWellFormed(): Boolean {
        val segs = segments
        for (i in 0 until segs.size - 2) {
            val seg = segs[i]
            for (j in i + 2 until segs.size) {
                if (i == 0 && j == segs.size - 1) {
                    continue
                }
                val otherSeg = segs[j]
                if (seg.intersects(otherSeg)) {
                    return false
                }
            }
        }
        return true
    }
}

fun PolygonD(origin: PointD, vectors: List<VectorD>, name: String = "") = PolygonD(vectors.runningFold(origin) { p, v -> p + v }, name = name)
