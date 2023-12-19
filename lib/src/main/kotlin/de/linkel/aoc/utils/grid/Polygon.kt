package de.linkel.aoc.utils.grid

import de.linkel.aoc.utils.iterables.contains
import de.linkel.aoc.utils.iterables.repeat
import kotlin.math.max
import kotlin.math.min

class Polygon(
    corners: List<Point>
) {
    val corners: List<Point>

    init {
        val set = corners.toSet()
        if (set.size < 3) {
            throw IllegalArgumentException("polygon needs at least 3 distinct points")
        }
        if (set.zipWithNext().map { (a, b) -> a - b }.zipWithNext().none { (a, b) -> a.determinant(b) != 0 }) {
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
    }

    val boundingBox get(): Rectangle {
        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        corners.forEach {
            minX = min(it.x, minX)
            maxX = max(it.x, maxX)
            minY = min(it.y, minY)
            maxY = max(it.y, maxY)
        }
        return Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1)
    }

    val area get(): Int {
        val vectors = corners.zipWithNext().map { (a, b) -> a - b }
        if (vectors.any { it.deltaX == 0 || it.deltaY == 0 }) throw IllegalStateException("can't calculate the area if there are non-90-degree turns")
        return pickTheorem(shoelaceFormula(corners), vectors.sumOf { it.manhattenDistance })
    }

    private fun shoelaceFormula(corners: List<Point>): Int =
        corners.zipWithNext().sumOf { (a, b) -> a.x * b.y - a.y * b.x } / 2

    private fun pickTheorem(area: Int, perimeter: Int): Int = area + perimeter / 2 + 1

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Polygon

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

fun Polygon(origin: Point, vectors: List<Vector>) = Polygon(vectors.runningFold(origin) { p, v -> p + v })
