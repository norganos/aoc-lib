package de.linkel.aoc.utils.iterables


fun <T> T.evolve(times: Int, lambda: (T, Int) -> T): T {
    return (0 until times)
        .fold(this) { acc, i ->
            lambda(acc, i)
        }
}

fun <T> List<T>.evolveElements(times: Int, lambda: (T, Int) -> T): List<T> {
    return (0 until times)
        .fold(this) { acc, i ->
            acc.map { lambda(it, i) }
        }
}

fun Int.times(lambda: (Int) -> Unit) {
    return (0 until this)
        .forEach(lambda)
}
