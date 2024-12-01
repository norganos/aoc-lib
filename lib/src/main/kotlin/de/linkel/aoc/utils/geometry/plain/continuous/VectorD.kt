package de.linkel.aoc.utils.geometry.plain.continuous

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.sign
import kotlin.math.sqrt

data class VectorD(
    val deltaX: Double,
    val deltaY: Double
) {
    companion object {
        val ZERO = VectorD(0.0, 0.0)
        val NORTH = VectorD(0.0, -1.0)
        val SOUTH = VectorD(0.0, 1.0)
        val WEST = VectorD(-1.0, 0.0)
        val EAST = VectorD(1.0, 0.0)
        val NORTHWEST = NORTH + WEST
        val NORTHEAST = NORTH + EAST
        val SOUTHWEST = SOUTH + WEST
        val SOUTHEAST = SOUTH + EAST
    }

    val length: Double = sqrt(deltaX.pow(2) + deltaY.pow(2))
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

    fun turnRight(): VectorD = VectorD(-deltaY, deltaX)
    fun turnLeft(): VectorD = VectorD(deltaY, -deltaX)
    fun turnAround(): VectorD = VectorD(-deltaX, -deltaY)

    fun determinant(other: VectorD): Double {
        return deltaX * other.deltaY - deltaY * other.deltaX
    }
    operator fun times(other: VectorD): Double {
        return deltaX * other.deltaX + deltaY * other.deltaY
    }
    infix fun parallel(other: VectorD): Boolean {
        return determinant(other) == 0.0
    }
    infix fun notParallel(other: VectorD): Boolean {
        return determinant(other) != 0.0
    }
    infix fun orthogonal(other: VectorD): Boolean {
        return times(other) == 0.0
    }

    override fun toString(): String {
        return "[${deltaX}/$deltaY]"
    }
}
