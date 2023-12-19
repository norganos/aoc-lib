package de.linkel.aoc.utils.iterables

import java.util.*

fun <T> Iterable<T>.repeat(n: Int): Sequence<T> {
    val input = this
    return sequence {
        repeat(n) {
            yieldAll(input)
        }
    }
}

fun <T> Iterable<T>.repeatForever(): Sequence<T> {
    val input = this
    return sequence {
        while(true) {
            yieldAll(input)
        }
    }
}

operator fun <T> List<T>.contains(list: List<T>): Boolean {
    return Collections.indexOfSubList(this, list) != -1
}
