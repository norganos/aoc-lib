package de.linkel.aoc.utils.graph

data class Node<K>(
    val id: K,
    val edges: Map<K, Int>
)

class Graph<K>(
    val nodes: Set<Node<K>>
) {
    private val network = nodes.associateBy { it.id }

    //TODO: add a lambda that gives cost from one K to another
    fun dijkstra(start: K, isDest: (id: K) -> Boolean): List<K>? {
        val max = this.network.size + 1
        val weightMap = network.mapValues { e -> DijkstraNode(e.key, if (e.key == start) 0 else max, null) }.toMutableMap()
        val points = weightMap.keys.toMutableSet()
        var dest: DijkstraNode<K>? = null
        while (points.isNotEmpty()) {
            val point = points.minBy { weightMap[it]!!.distance }
            val pointWeightData = weightMap[point]!!
            points.remove(point)
            network[point]!!.edges
                .filter { it.key in points }
                .forEach {
                    weightMap[it.key] = weightMap[it.key]!!.copy(distance = pointWeightData.distance + it.value, before = point)
                }
            if (isDest(point)) {
                dest = pointWeightData
                break
            }
        }
        return if (dest != null) {
            var prev: DijkstraNode<K>? = dest
            val result = mutableListOf<K>()
            while (prev != null) {
                result.add(0, prev.id)
                prev = prev.before?.let { weightMap[it] }
            }
            result.toList()
        } else {
            null
        }
    }

    fun dfs(start: K, isDest: (id: K) -> Boolean): List<K>? {
        return dfsStep(start, isDest, emptyList())
    }

    private fun dfsStep(pos: K, isDest: (id: K) -> Boolean, path: List<K>): List<K>? {
        return if (isDest(pos)) path
        else network[pos]!!.edges.entries
            .sortedBy { it.value }
            .filter { it.key !in path }
            .firstNotNullOfOrNull {
                dfsStep(it.key, isDest, path + listOf(it.key))
            }
    }

    fun bfs(start: K, isDest: (id: K) -> Boolean): List<K>? {
        if (isDest(start)) {
            return listOf(start)
        }
        return bfsStep(start, isDest, emptyList())
    }

    private fun bfsStep(pos: K, isDest: (id: K) -> Boolean, path: List<K>): List<K>? {
        return network[pos]!!.edges.entries
            .sortedBy { it.value }
            .filter { it.key !in path }
            .let { possibleNext ->
                possibleNext
                    .firstNotNullOfOrNull {
                        if (isDest(it.key)) path + listOf(it.key) else null
                    }
                ?: possibleNext
                    .firstNotNullOfOrNull {
                        bfsStep(it.key, isDest, path + listOf(it.key))
                    }
            }
    }

    fun subGraphs(): Iterable<Graph<K>> {
        val results = mutableListOf<Set<Node<K>>>()
        val all = network.keys.toMutableSet()
        while (all.isNotEmpty()) {
            val first = all.first()
            val current = mutableSetOf<K>()
            val queue = mutableListOf(first)
            while (queue.isNotEmpty()) {
                val node = queue.removeAt(0)
                current.add(node)
                queue.addAll(
                    network[node]!!.edges.keys
                       .filter { it !in current }
                )
            }
            results.add(current.map { network[it]!! }.toSet())
            all.removeAll(current)
        }
        return results
            .map { Graph(it) }
    }

    data class DijkstraNode<K>(
        val id: K,
        val distance: Int,
        val before: K?
    )
}

class GraphBuilder<K> {
    private val nodes = mutableMapOf<K, Map<K, Int>>()

    fun node(id: K): GraphBuilder<K> {
        nodes[id] = nodes[id] ?: emptyMap()
        return this
    }

    fun edge(from: K, to: K, weight: Int = 1, bidirectional: Boolean = false): GraphBuilder<K> {
        nodes[from] = (nodes[from] ?: emptyMap()) + mapOf(to to weight)
        if (bidirectional) {
            nodes[to] = (nodes[to] ?: emptyMap()) + mapOf(from to weight)
        } else {
            nodes[to] = (nodes[to] ?: emptyMap())
        }
        return this
    }

    fun build(): Graph<K> {
        return Graph(
            nodes.entries
                .map { Node(it.key, it.value)}
                .toSet()
        )
    }
}
