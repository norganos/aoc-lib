package de.linkel.aoc.lib.computer

data class CommandLine(
    val command: String,
    val arguments: List<String> = emptyList()
)
