package de.linkel.aoc.utils.readers

import java.io.Reader

fun Reader.charSequence(): Sequence<Char> {
    val reader = this
    return sequence {
        while (true) {
            val i = reader.read()
            if (i >= 0) yield(i.toChar())
            else break
        }
    }
}
