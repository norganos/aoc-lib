package de.linkel.aoc.utils.math.algebra

import java.math.BigDecimal

data class Matrix(
    val cols: Int,
    val rows: Int,
    val values: List<BigDecimal>,
) {
    init {
        require(rows > 0) { "rows must be greater than zero" }
        require(cols > 0) { "cols must be greater than zero" }
        require(values.size == rows * cols) { "wrong number of values" }
    }

    fun det(): BigDecimal {
        require(rows == cols) { "only square matrices can be determined" }
        return when (rows) {
            1 -> values[0]
            2 -> values[0] * values[3] - values[1] * values[2]
            else -> {
                val withoutFirstCol = this.removeCol(0)
                val minusOne = BigDecimal.ONE.negate()
                (0 until rows)
                    .sumOf { idx ->
                        (minusOne.pow(idx)) * values[idx * cols] * withoutFirstCol.removeRow(idx).det()
                    }
            }
        }
    }

    fun getRow(idx: Int): List<BigDecimal> {
        require(idx in 0 until rows) { "row index out of bounds" }
        val select = (cols * idx) until (cols * (idx + 1))
        return values.filterIndexed { i, _ -> i in select }
    }

    fun getCol(idx: Int): List<BigDecimal> {
        require(idx in 0 until cols) { "col index out of bounds" }
        return values.filterIndexed { i, _ -> i % cols == idx }
    }

    fun getAt(col: Int, row: Int): BigDecimal {
        require(col in 0 until cols) { "col index out of bounds" }
        require(row in 0 until rows) { "col index out of bounds" }
        return values[row * cols + col]
    }

    fun removeRow(idx: Int): Matrix {
        require(idx in 0 until rows) { "row index out of bounds" }
        require(rows > 1) { "matrix must have more than one row" }
        val remove = (cols * idx) until (cols * (idx + 1))
        return Matrix(
            cols = cols,
            rows = rows - 1,
            values = values.filterIndexed { i, _ -> i !in remove })
    }

    fun removeCol(idx: Int): Matrix {
        require(idx in 0 until cols) { "col index out of bounds" }
        require(cols > 1) { "matrix must have more than one col" }
        return Matrix(
            cols = cols - 1,
            rows = rows,
            values = values.filterIndexed { i, _ -> i % cols != idx }
        )
    }

    fun insertRow(idx: Int, row: List<BigDecimal>): Matrix {
        require(idx in 0 until rows+1) { "row index out of bounds" }
        require(row.size == cols) { "wrong number of values" }
        return Matrix(
            cols = cols,
            rows = rows + 1,
            values = values.subList(0, idx * cols) + row + values.subList(idx * cols, values.size)
        )
    }

    fun replaceRow(idx: Int, row: List<BigDecimal>): Matrix {
        require(idx in 0 until rows) { "row index out of bounds" }
        require(row.size == cols) { "wrong number of values" }
        return Matrix(
            cols = cols,
            rows = rows,
            values = values.subList(0, idx * cols) + row + values.subList((idx + 1) * cols, values.size)
        )
    }

    fun insertCol(idx: Int, col: List<BigDecimal>): Matrix {
        require(idx in 0 until cols+1) { "col index out of bounds" }
        require(col.size == rows) { "wrong number of values" }
        return Matrix(
            cols = cols + 1,
            rows = rows,
            values = (0 until (cols + 1) * rows).map { i ->
                val c = i % (cols + 1)
                val r = i / (cols + 1)
                if (c < idx)
                    values[r * cols + c]
                else if (c == idx)
                    col[r]
                else values[r * cols + c - 1]
            }
        )
    }

    fun replaceCol(idx: Int, col: List<BigDecimal>): Matrix {
        require(idx in 0 until cols) { "col index out of bounds" }
        require(col.size == rows) { "wrong number of values" }
        return Matrix(
            cols = cols,
            rows = rows,
            values = values.mapIndexed { i, v ->
                val c = i % cols
                val r = i / cols
                if (c == idx)
                    col[r]
                else
                    v
            }
        )
    }

    fun appendRow(row: List<BigDecimal>): Matrix {
        require(row.size == cols) { "wrong number of values" }
        return Matrix(
            cols = cols,
            rows = rows + 1,
            values = values + row
        )
    }

    fun appendCol(col: List<BigDecimal>): Matrix {
        require(col.size == rows) { "wrong number of values" }
        return Matrix(
            cols = cols + 1,
            rows = rows,
            values = (0 until rows).flatMap { r ->
                values.subList(r * cols, (r + 1) * cols) + col[r]
            }
        )
    }

    fun transpose(): Matrix {
        return Matrix(
            cols = rows,
            rows = cols,
            (0 until cols)
                .flatMap { c ->
                    (0 until rows)
                        .map { r -> getAt(c, r) }
                }
        )
    }
}