package de.linkel.aoc.lib.computer

interface CommandLineParser {
    fun parse(readable: Readable): CommandLine
}
