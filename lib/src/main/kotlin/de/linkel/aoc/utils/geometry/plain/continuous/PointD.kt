package de.linkel.aoc.utils.geometry.plain.continuous

data class PointD(
    val x: Double,
    val y: Double
) {
    operator fun plus(v: VectorD): PointD {
        return copy(
            x = x + v.deltaX,
            y = y + v.deltaY
        )
    }
    operator fun minus(p: PointD): VectorD {
        return VectorD(
            deltaX = x - p.x,
            deltaY = y - p.y
        )
    }
    operator fun minus(v: VectorD): PointD {
        return copy(
            x = x - v.deltaX,
            y = y - v.deltaY
        )
    }

    operator fun rangeTo(p: PointD): SegmentD {
        return SegmentD(this, p)
    }

    override fun toString(): String {
        return "$x/$y"
    }
}
