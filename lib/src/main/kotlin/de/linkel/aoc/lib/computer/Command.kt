package de.linkel.aoc.lib.computer

interface Command {
    fun execute(commandContext: CommandContext)
    fun append(line: String)
    fun close()
}
