package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.PolygonD
import de.linkel.aoc.utils.iterables.contains
import de.linkel.aoc.utils.iterables.repeat
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sign

class Polygon(
    corners: List<Point>,
    val name: String = "",
): Shape<Polygon> {
    override val corners: List<Point>
    override val boundingBox: Rectangle

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

        var minX = Int.MAX_VALUE
        var minY = Int.MAX_VALUE
        var maxX = Int.MIN_VALUE
        var maxY = Int.MIN_VALUE
        this.corners.forEach {
            minX = min(it.x, minX)
            maxX = max(it.x, maxX)
            minY = min(it.y, minY)
            maxY = max(it.y, maxY)
        }
        boundingBox = Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1)
    }

    override val area get(): Int {
        val vectors = corners.zipWithNext().map { (a, b) -> a - b }
        return if (vectors.all { it.deltaX == 0 || it.deltaY == 0 }) {
            // good algorithm for shapes with only right angles
            pickTheorem(shoelaceFormula(corners), vectors.sumOf { it.manhattanDistance })
        } else {
            // horrible algorithm: we count how many points of the boundingBox are inside
            boundingBox.points.count { contains(it) }
        }
    }

    override val segments get(): List<Segment> = corners.zipWithNext().map { (a, b) -> Segment(a, b - a) }

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

    override fun contains(point: Point): Boolean {
        return boundingBox.contains(point) && (
                corners
                    .zipWithNext()
                    .filter { (a, b) -> (a.y > point.y) != (b.y > point.y) }
                    .count { (a, b) -> point.x < (a.x + (b.x - a.x).toDouble() / (b.y - a.y) * (point.y - a.y)) }
                    .isOdd()
                || segments.any { point in it }
            )
//
//        var crossings = 0
//        val size = corners.size
//        for (i in 0 until size - 1) {
//            val a = corners[i]
//            val b = corners[i + 1]
//            if ((a.y > point.y) != (b.y > point.y)) {
//                val slope = (b.x - a.x).toDouble() / (b.y - a.y)
//                val xIntercept = a.x + slope * (point.y - a.y)
//                if (point.x < xIntercept) crossings++
//            }
//        }
//        return crossings % 2 != 0
    }

    override fun plus(vector: Vector): Polygon {
        return Polygon(corners.map { it + vector }, name)
    }

    override fun minus(vector: Vector): Polygon {
        return Polygon(corners.map { it + vector }, name)
    }

    override fun times(factor: Int): Polygon {
        TODO("Not yet implemented")
    }

    override fun intersects(segment: Segment): Boolean {
        return segment.start in this || segment.end in this || segments.any { polygonSegment -> polygonSegment.intersects(segment) }
    }

    override fun intersects(shape: Shape<*>): Boolean {
        return boundingBox.intersects(shape.boundingBox) && shape.segments.any { this.intersects(it) }
    }

    private fun Int.isOdd() = this % 2 != 0

    fun toContinuous() = PolygonD(corners.map { it.toContinuous() })

    fun toAnonymous() = Polygon(corners)

    override fun toString(): String {
        return "$name ${corners.take(corners.size-1).joinToString("->")}".trim()
    }

    fun isConcave(): Boolean {
//        TODO("Not yet implemented")
        // kann ich hier einfach die winkel zwicshen den segmenten betrachten?
        // dass quasi alle winkel in dieselbe richtung (rechts/links) zeigen muessen,
        // dann isses konkav?
        return (segments.plusElement(segments.first()))
            .zipWithNext { a, b -> a.vector.determinant(b.vector) }
            .filter { it != 0 }
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

fun Polygon(origin: Point, vectors: List<Vector>, name: String = "") = Polygon(vectors.runningFold(origin) { p, v -> p + v }, name)
