package de.linkel.aoc.utils.geometry.plain.continuous

class PointD(
    val x: Double,
    val y: Double,
    override val name: String = ""
): ShapeD<PointD> {
    companion object {
        val ZERO = PointD(0.0, 0.0)
    }

    override val boundingBox: RectangleD
        get() = throw IllegalStateException("no bounding box for point")
    override val segments: List<SegmentD>
        get() = emptyList()
    override val corners: List<PointD>
        get() = listOf(this)
    override val area: Double
        get() = 0.0

    override operator fun plus(vector: VectorD): PointD {
        return PointD(
            x = x + vector.deltaX,
            y = y + vector.deltaY,
            name = name
        )
    }
    operator fun minus(p: PointD): VectorD {
        return VectorD(
            deltaX = x - p.x,
            deltaY = y - p.y
        )
    }
    override operator fun minus(vector: VectorD): PointD {
        return PointD(
            x = x - vector.deltaX,
            y = y - vector.deltaY,
            name = name
        )
    }

    override fun times(factor: Double): PointD {
        return this
    }

    override fun contains(point: PointD): Boolean {
        return point == this
    }

    override fun intersects(shape: ShapeD<*>): Boolean {
        return shape.contains(this)
    }

    operator fun rangeTo(p: PointD): SegmentD {
        return SegmentD(this, p)
    }

    override fun toString(): String {
        return "$name $x/$y".trim()
    }
    override fun toAnonymous() = PointD(
        x = x,
        y = y
    )
    override fun named(name: String) = PointD(
        x = x,
        y = y,
        name = name
    )

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointD

        if (x != other.x) return false
        if (y != other.y) return false

        return true
    }

    override fun hashCode(): Int {
        var result = x.hashCode()
        result = 31 * result + y.hashCode()
        return result
    }
}
