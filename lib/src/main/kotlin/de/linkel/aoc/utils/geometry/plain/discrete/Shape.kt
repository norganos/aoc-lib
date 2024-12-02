package de.linkel.aoc.utils.geometry.plain.discrete

interface Shape<T: Shape<T>> {
    val boundingBox: Rectangle
    val segments: List<Segment>
    val corners: List<Point>
    val area: Int
    val name: String

    operator fun plus(vector: Vector): T
    operator fun minus(vector: Vector): T
    operator fun times(factor: Int): T

    operator fun contains(point: Point): Boolean

    fun intersects(shape: Shape<*>): Boolean

    fun toAnonymous(): T
    fun named(name: String): T
    //TODO: toContinuous hier deklarieren?
}
