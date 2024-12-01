package de.linkel.aoc.utils.geometry.plain.continuous

import de.linkel.aoc.utils.iterables.contains
import de.linkel.aoc.utils.iterables.repeat
import kotlin.math.max
import kotlin.math.min

class PolygonD(
    corners: List<PointD>
) {
    val corners: List<PointD>

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

        val pointsWithSegment = this.corners
            .zipWithNext()
            .flatMapIndexed { idx, (a, b) -> listOf(a to idx, b to idx) }
            .sortedWith(compareBy({ it.first.x }, { it.first.y }))
    }

    val boundingBox get(): RectangleD {
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
        return RectangleD(minX, minY, maxX - minX, maxY - minY)
    }

    val area get(): Double {
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
        if (corners.toSet() != other.corners.toSet()) {
            return false
        }
        if (corners in other.corners.repeat(2).toList()) {
            return true
        }
        return false
    }

    override fun hashCode(): Int {
        // hier bewusst set-ähnliche implementierung, so dass Rotation der Punkte denselben Hash ergibt
        return corners.sumOf { it.hashCode() }
    }
}

fun PolygonD(origin: PointD, vectors: List<VectorD>) = PolygonD(vectors.runningFold(origin) { p, v -> p + v })
