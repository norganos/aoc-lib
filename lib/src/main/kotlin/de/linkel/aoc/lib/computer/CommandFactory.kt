package de.linkel.aoc.lib.computer

interface CommandFactory {
    fun create(commandLine: CommandLine): Command?
}
