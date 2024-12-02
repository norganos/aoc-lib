package de.linkel.aoc.utils.geometry.plain.discrete

import de.linkel.aoc.utils.geometry.plain.continuous.VectorD
import kotlin.math.*

class Vector(
    val deltaX: Int,
    val deltaY: Int
) {
    companion object {
        val ZERO = Vector(0, 0)
        val NORTH = Vector(0, -1)
        val SOUTH = Vector(0, 1)
        val WEST = Vector(-1, 0)
        val EAST = Vector(1, 0)
        val NORTH_WEST = Vector(-1, -1)
        val NORTH_EAST = Vector(1, -1)
        val SOUTH_WEST = Vector(-1, 1)
        val SOUTH_EAST = Vector(1, 1)
    }

    val manhattanDistance = abs(deltaX) + abs(deltaY)
    val length = sqrt(deltaX.toDouble().pow(2) + deltaY.toDouble().pow(2))
    val maximumAxisDistance get(): Int = max(abs(deltaX), abs(deltaY))
    val isVertical get(): Boolean = deltaX == 0
    val isHorizontal get(): Boolean = deltaY == 0

    operator fun times(factor: Int): Vector {
        return Vector(factor * deltaX, factor * deltaY)
    }
    operator fun plus(other: Vector): Vector {
        return Vector(deltaX + other.deltaX, deltaY + other.deltaY)
    }
    operator fun minus(other: Vector): Vector {
        return Vector(deltaX - other.deltaX, deltaY - other.deltaY)
    }
    operator fun unaryMinus() = Vector(-deltaX, -deltaY)

    operator fun div(divisor: Int): Vector {
        if (divisor == 0)
            throw ArithmeticException("division by zero")
        if (deltaX % divisor != 0 || deltaY % divisor != 0)
            throw IllegalArgumentException("divisor has to be a common factor of deltaX and deltaY")
        return Vector(deltaX / divisor, deltaY / divisor)
    }
    operator fun contains(other: Vector): Boolean {
        return this parallel other
                && this.deltaX.sign == other.deltaX.sign
                && this.deltaY.sign == other.deltaY.sign
                && abs(this.deltaX) >= abs(other.deltaX)
                && abs(this.deltaY) >= abs(other.deltaY)
    }

    fun turnClockwise(): Vector = Vector(-deltaY, deltaX)
    fun turnCounterClockwise(): Vector = Vector(deltaY, -deltaX)
    fun turnAround(): Vector = Vector(-deltaX, -deltaY)

    fun determinant(other: Vector): Int {
        return deltaX * other.deltaY - deltaY * other.deltaX
    }
    operator fun times(other: Vector): Int {
        return deltaX * other.deltaX + deltaY * other.deltaY
    }
    infix fun parallel(other: Vector): Boolean {
        return this == other || (this != ZERO && other != ZERO && determinant(other) == 0)
    }
    infix fun notParallel(other: Vector): Boolean {
        return this != other && (this == ZERO || other == ZERO || determinant(other) != 0)
    }
    infix fun orthogonal(other: Vector): Boolean {
        return this != ZERO && other != ZERO && times(other) == 0
    }

    override fun toString(): String {
        return "[${deltaX}/$deltaY]"
    }

    fun toContinuous() = VectorD(deltaX.toDouble(), deltaY.toDouble())

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Vector

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
