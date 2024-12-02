package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.*

class VectorD(
    val deltaX: Double,
    val deltaY: Double
) {
    companion object {
        val ZERO = VectorD(0.0, 0.0)
        val NORTH = VectorD(0.0, -1.0)
        val SOUTH = VectorD(0.0, 1.0)
        val WEST = VectorD(-1.0, 0.0)
        val EAST = VectorD(1.0, 0.0)
        val NORTH_WEST = NORTH + WEST
        val NORTH_EAST = NORTH + EAST
        val SOUTH_WEST = SOUTH + WEST
        val SOUTH_EAST = SOUTH + EAST
    }

    val length: Double = sqrt(deltaX.pow(2) + deltaY.pow(2))
    val maximumAxisDistance get(): Double = max(abs(deltaX), abs(deltaY))
    val isVertical get(): Boolean = deltaX == 0.0
    val isHorizontal get(): Boolean = deltaY == 0.0

    operator fun times(factor: Double): VectorD {
        return VectorD(factor * deltaX, factor * deltaY)
    }
    operator fun plus(other: VectorD): VectorD {
        return VectorD(deltaX + other.deltaX, deltaY + other.deltaY)
    }
    operator fun minus(other: VectorD): VectorD {
        return VectorD(deltaX - other.deltaX, deltaY - other.deltaY)
    }
    operator fun unaryMinus() = VectorD(-deltaX, -deltaY)
    operator fun div(divisor: Double): VectorD {
        if (divisor == 0.0)
            throw ArithmeticException("division by zero")
        return VectorD(deltaX / divisor, deltaY / divisor)
    }
    operator fun contains(other: VectorD): Boolean {
        return this parallel other
                && this.deltaX.sign == other.deltaX.sign
                && this.deltaY.sign == other.deltaY.sign
                && abs(this.deltaX) >= abs(other.deltaX)
                && abs(this.deltaY) >= abs(other.deltaY)
    }

    fun turnClockwise(): VectorD = VectorD(-deltaY, deltaX)
    fun turnCounterClockwise(): VectorD = VectorD(deltaY, -deltaX)
    fun turnAround(): VectorD = VectorD(-deltaX, -deltaY)

    fun determinant(other: VectorD): Double {
        return deltaX * other.deltaY - deltaY * other.deltaX
    }
    operator fun times(other: VectorD): Double {
        return deltaX * other.deltaX + deltaY * other.deltaY
    }
    infix fun parallel(other: VectorD): Boolean {
        return this == other || (this != ZERO && other != ZERO && determinant(other) == 0.0)
    }
    infix fun notParallel(other: VectorD): Boolean {
        return this != other && (this == ZERO || other == ZERO || determinant(other) != 0.0)
    }
    infix fun orthogonal(other: VectorD): Boolean {
        return this != ZERO && other != ZERO && times(other) == 0.0
    }

    override fun toString(): String {
        return "[${deltaX}/$deltaY]"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as VectorD

        if (deltaX != other.deltaX) return false
        if (deltaY != other.deltaY) return false

        return true
    }

    override fun hashCode(): Int {
        var result = deltaX.hashCode()
        result = 31 * result + deltaY.hashCode()
        return result
    }
}
