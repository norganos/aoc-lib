package de.linkel.aoc.utils.math.algebra

import java.math.BigDecimal

object Equations {
    fun cramer(coefficients: Matrix, results: List<BigDecimal>): List<BigDecimal>? {
        require(coefficients.rows == coefficients.cols) { "coefficients matrix has to be square" }
        require(results.size == coefficients.rows) { "results vector has to be same size as coefficients rows" }
        val denominator = coefficients.det()
        return (0 until coefficients.cols)
            .map { i ->
                coefficients.replaceCol(i, results).det()
            }
            .takeIf { numerators ->
                numerators
                    .all { it % denominator == BigDecimal.ZERO}
            }
            ?.map {
                it / denominator
            }
    }
}