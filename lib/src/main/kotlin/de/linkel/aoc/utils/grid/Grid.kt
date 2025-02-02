package de.linkel.aoc.utils.grid

import de.linkel.aoc.utils.geometry.plain.discrete.Dimension
import de.linkel.aoc.utils.geometry.plain.discrete.Point
import de.linkel.aoc.utils.geometry.plain.discrete.Rectangle
import de.linkel.aoc.utils.geometry.plain.discrete.Vector

class Grid<T: Any>(
    origin: Point = Point(0,0),
    dimension: Dimension = Dimension(1,1)
) {
    companion object {
        fun <T: Any> parse(lines: Sequence<String>, crop: Boolean = true, lambda: (pos: Point, c: Char) -> T?): Grid<T> {
            val grid = Grid<T>()
            lines
                .filter { it.isNotEmpty() }
                .forEachIndexed { y, line ->
                    val chars = line
                        .toCharArray()
                    chars
                        .forEachIndexed { x, c ->
                            val p = Point(x, y)
                            grid.stretchTo(p)
                            val t = lambda(p, c)
                            if (t != null) {
                                grid[p] = t
                            }
                        }
                }
            if (crop)
                grid.crop()
            return grid
        }
    }

    var boundingBox = Rectangle(
        x = origin.x,
        y = origin.y,
        width = dimension.width,
        height = dimension.height
    )

    fun straightLine(start: Point, direction: Vector): List<Point> {
        val result = mutableListOf<Point>()
        var pos = start
        while (pos in boundingBox) {
            result.add(pos)
            pos += direction
        }
        return result
    }

    fun walkUntil(start: Point, vector: Vector, stop: (point: Point) -> Boolean): Point {
        var pos = start
        while (true) {
            pos += vector
            if (stop(pos)) return pos
        }
    }

    // evtl nen performance-optimierteren zugriff? / ne liste aller belegten punkte pro row/col?
    private val store = mutableMapOf<Point, T>()
    val width get(): Int = boundingBox.width
    val height get(): Int = boundingBox.height
    val area get(): Int = boundingBox.width * boundingBox.height

    val size get(): Int = store.size

    fun crop() {
        boundingBox = getDataBoundingBox()
    }
    fun stretchTo(point: Point) {
        boundingBox = boundingBox.extendTo(point)
    }

    private fun checkPoint(point: Point) {
        if (point !in boundingBox) {
            throw IllegalArgumentException("coordinates $point out of bounds ($boundingBox)")
        }
    }

    operator fun contains(point: Point): Boolean {
        return point in store
    }

    operator fun get(pos: Point): T? {
        checkPoint(pos)
        return store[pos]
    }

    operator fun set(pos: Point, value: T?) {
        checkPoint(pos)
        if (value == null) {
            store.remove(pos)
        } else {
            store[pos] = value
        }
    }

    fun getDataBoundingBox(): Rectangle {
        val minX = store.keys.minOf { it.x }
        val minY = store.keys.minOf { it.y }
        val maxX = store.keys.maxOf { it.x }
        val maxY = store.keys.maxOf { it.y }
        return Rectangle(minX, minY, maxX - minX + 1, maxY - minY + 1)
    }

    @Suppress("unused")
    fun getRow(y: Int): List<DataPoint<T?>> {
        return List(boundingBox.width) { dx ->
            val p = Point(boundingBox.x + dx, y)
            DataPoint(p, store[p])
        }
    }

    fun getRowData(y: Int): List<DataPoint<T>> {
        return List(boundingBox.width) { dx ->
                Point(boundingBox.x + dx, y)
            }
            .filter { store[it] != null }
            .map { DataPoint(it, store[it]!!) }
            .toList()
    }

    @Suppress("unused")
    fun getCol(x: Int): List<DataPoint<T?>> {
        return List(boundingBox.height) { dy ->
            val p = Point(x, boundingBox.y + dy)
            DataPoint(p, store[p])
        }
    }

    fun getColData(x: Int): List<DataPoint<T>> {
        return List(boundingBox.height) { dy ->
                Point(x, boundingBox.y + dy)
            }
            .filter { store[it] != null }
            .map { DataPoint(it, store[it]!!) }
            .toList()
    }

    @Suppress("unused")
    fun getBeams(pos: Point): List<List<DataPoint<T>>> {
        val row = getRowData(pos.y)
        val col = getColData(pos.x)
        return listOf(
            col.filter { it.point.y < pos.y }.sortedByDescending { it.point.y },
            row.filter { it.point.x > pos.x }.sortedBy { it.point.x },
            col.filter { it.point.y > pos.y }.sortedBy { it.point.y },
            row.filter { it.point.x < pos.x }.sortedByDescending { it.point.x }
        )
    }

    @Suppress("unused")
    fun getAllData(): List<DataPoint<T>> {
        return store.entries
            .map {
                DataPoint(it.key, it.value)
            }
    }

    fun <R: Any> transform(lambda: (pos: Point, data: T) -> R?): Grid<R> {
        return Grid<R>(boundingBox.origin, boundingBox.dimension)
            .let { other ->
                store.entries.forEach { entry ->
                    val r = lambda(entry.key, entry.value)
                    if (r != null) {
                        other.store[entry.key] = r
                    }
                }
                other
            }
    }

    @Suppress("unused")
    fun <R: Any> transformComplete(lambda: (points: Map<Point, T>) -> Map<Point, R>): Grid<R> {
        return Grid<R>(boundingBox.origin, boundingBox.dimension)
            .let { other ->
                other.store.putAll(lambda(store))
                other
            }
    }

    @Suppress("unused")
    fun copy(): Grid<T> {
        return Grid<T>(boundingBox.origin, boundingBox.dimension)
            .let { other ->
                store.entries.forEach { entry ->
                    other.store[entry.key] = entry.value
                }
                other
            }
    }

    @Suppress("unused")
    fun filterData(lambda: (pos: Point, data: T) -> Boolean): List<DataPoint<T>> {
        return store.entries
            .filter { lambda(it.key, it.value) }
            .map {
                DataPoint(it.key, it.value)
            }
    }

    @Suppress("unused")
    fun isNotEmpty(): Boolean {
        return store.isNotEmpty()
    }

    @Suppress("unused")
    fun isEmpty(): Boolean {
        return store.isEmpty()
    }

    private val directions4 = listOf(
        Vector.NORTH,
        Vector.EAST,
        Vector.SOUTH,
        Vector.WEST
    )
    private val directions8 = listOf(
        Vector.NORTH,
        Vector.NORTH_EAST,
        Vector.EAST,
        Vector.SOUTH_EAST,
        Vector.SOUTH,
        Vector.SOUTH_WEST,
        Vector.WEST,
        Vector.NORTH_WEST
    )

    @Suppress("unused")
    fun getNeighbours(point: Point, diagonal: Boolean = false): List<DataPoint<T>> {
        return (if (diagonal) directions8 else directions4)
            .map { point + it }
            .filter { store.containsKey(it) }
            .map { DataPoint(it, store[it]!!)}
    }

    @Suppress("unused")
    fun dijkstra(start: Point, isDest: (point: DataPoint<T>) -> Boolean, getNeighbours: (from: DataPoint<T>) -> Collection<Point>): List<DataPoint<T>>? {
        val max = this.area + 1
        val weightMap = transform { p, d -> DijkstraNode(d, if (p == start) 0 else max, null) }
        val points = weightMap.getAllData().map { it.point }.toMutableSet()
        var dest: Point? = null
        while (points.isNotEmpty()) {
            val point = points.minBy {  weightMap[it]!!.distance }
            val pointWeightData = weightMap[point]!!
            val dataPoint = DataPoint(point, pointWeightData.data)
            points.remove(point)
            getNeighbours(dataPoint)
                .filter { it in weightMap }
                .filter { it in points }
                .forEach {
                    weightMap[it] = weightMap[it]!!.copy(distance = pointWeightData.distance + 1, before = point)
                }
            if (isDest(dataPoint)) {
                dest = point
                break
            }
        }
        return if (dest != null) {
            var prev: Point? = dest
            val result = mutableListOf<DataPoint<T>>()
            while (prev != null) {
                val prevWeightData = weightMap[prev]!!
                result.add(0, DataPoint(prev, prevWeightData.data))
                prev = prevWeightData.before
            }
            result.toList()
        } else {
            null
        }
    }

    data class DijkstraNode<T>(
        val data: T,
        val distance: Int,
        val before: Point?
    )
}

@Suppress("unused")
infix fun <T : Any> Point.inside(grid: Grid<T>): Boolean {
    return this in grid.boundingBox
}
