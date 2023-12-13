package de.linkel.aoc.utils.iterables


fun <T> List<T>.withoutIndex(blacklist: IntRange) = this.filterIndexed { idx, _ -> idx !in blacklist }
fun <T> List<T>.withoutIndex(index: Int) = this.filterIndexed { idx, _ -> idx != index }
