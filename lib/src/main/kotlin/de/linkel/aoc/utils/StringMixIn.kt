package de.linkel.aoc.utils

fun CharSequence.replaceIndex(idx: Int, char: Char): CharSequence {
    if (idx < 0 || idx >= length)
        throw IndexOutOfBoundsException("index $idx is out of bounds 0..${length-1}")
    val sb = StringBuilder()
    sb.appendRange(this, 0, idx)
    sb.append(char)
    sb.appendRange(this, idx + 1, length)
    return sb
}

fun String.replaceIndex(idx: Int, char: Char): String =
    (this as CharSequence).replaceIndex(idx, char).toString()
