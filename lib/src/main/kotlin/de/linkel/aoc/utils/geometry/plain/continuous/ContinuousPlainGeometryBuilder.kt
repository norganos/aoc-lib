package de.linkel.aoc.utils.geometry.plain.continuous

@Suppress("unused")
class ContinuousPlainGeometryBuilder {
    fun point(x: Double, y: Double, name: String = "") = PointD(
        x = x,
        y = y,
        name = name
    )

    fun vector(deltaX: Double, deltaY: Double) = VectorD(
        deltaX = deltaX,
        deltaY = deltaY
    )

    fun dimension(width: Double, height: Double) = DimensionD(
        width = width,
        height = height
    )

    fun segment(start: PointD, end: PointD, name: String = "") = SegmentD(
        start = start,
        end = end,
        name = name
    )

    fun segment(start: PointD, vector: VectorD, name: String = "") = SegmentD(
        start = start,
        vector = vector,
        name = name
    )

    fun rectangle(start: PointD, end: PointD, name: String = "") = RectangleD(
        a = start,
        b = end,
        name = name
    )

    fun rectangle(origin: PointD, dimension: DimensionD, name: String = "") = RectangleD(
        origin = origin,
        dimension = dimension,
        name = name
    )

    fun rectangle(x: Double, y: Double, width: Double, height: Double, name: String = "") = RectangleD(
        x = x,
        y = y,
        width = width,
        height = height,
        name = name
    )

    fun polygon(corners: List<PointD>, name: String = "") = PolygonD(
        corners = corners,
        name = name
    )

    fun polygon(origin: PointD, vectors: List<VectorD>, name: String = "") = PolygonD(
        origin = origin,
        vectors = vectors,
        name = name
    )

    fun triangle(a: PointD, b: PointD, c: PointD, name: String = "") = PolygonD(
        corners = listOf(a, b, c),
        name = name
    )

    fun triangle(origin: PointD, a: VectorD, b: VectorD, name: String = "") = PolygonD(
        corners = listOf(origin, origin + a, origin + b),
        name = name
    )
}