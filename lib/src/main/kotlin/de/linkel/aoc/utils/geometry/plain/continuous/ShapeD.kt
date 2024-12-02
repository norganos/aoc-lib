package de.linkel.aoc.utils.geometry.plain.continuous

interface ShapeD<T: ShapeD<T>> {
    val boundingBox: RectangleD
    val segments: List<SegmentD>
    val corners: List<PointD>
    val area: Double
    val name: String

    operator fun plus(vector: VectorD): T
    operator fun minus(vector: VectorD): T
    operator fun times(factor: Double): T

    operator fun contains(point: PointD): Boolean

    fun intersects(shape: ShapeD<*>): Boolean

    fun toAnonymous(): T
    fun named(name: String): T
}
