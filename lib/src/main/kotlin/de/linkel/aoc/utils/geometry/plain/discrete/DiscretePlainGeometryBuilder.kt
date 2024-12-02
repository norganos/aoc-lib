package de.linkel.aoc.utils.geometry.plain.discrete

@Suppress("unused")
class DiscretePlainGeometryBuilder {
    fun point(x: Int, y: Int, name: String = "") = Point(
        x = x,
        y = y,
        name = name
    )

    fun vector(deltaX: Int, deltaY: Int) = Vector(
        deltaX = deltaX,
        deltaY = deltaY
    )

    fun dimension(width: Int, height: Int) = Dimension(
        width = width,
        height = height
    )

    fun segment(start: Point, end: Point, name: String = "") = Segment(
        start = start,
        end = end,
        name = name
    )

    fun segment(start: Point, vector: Vector, name: String = "") = Segment(
        start = start,
        vector = vector,
        name = name
    )

    fun rectangle(start: Point, end: Point, name: String = "") = Rectangle(
        a = start,
        b = end,
        name = name
    )

    fun rectangle(origin: Point, dimension: Dimension, name: String = "") = Rectangle(
        origin = origin,
        dimension = dimension,
        name = name
    )

    fun rectangle(x: Int, y: Int, width: Int, height: Int, name: String = "") = Rectangle(
        x = x,
        y = y,
        width = width,
        height = height,
        name = name
    )

    fun polygon(corners: List<Point>, name: String = "") = Polygon(
        corners = corners,
        name = name
    )

    fun polygon(origin: Point, vectors: List<Vector>, name: String = "") = Polygon(
        origin = origin,
        vectors = vectors,
        name = name
    )

    fun triangle(a: Point, b: Point, c: Point, name: String = "") = Polygon(
        corners = listOf(a, b, c),
        name = name
    )

    fun triangle(origin: Point, a: Vector, b: Vector, name: String = "") = Polygon(
        corners = listOf(origin, origin + a, origin + b),
        name = name
    )
}