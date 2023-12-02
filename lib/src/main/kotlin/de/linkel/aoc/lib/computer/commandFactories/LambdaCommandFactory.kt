package de.linkel.aoc.lib.computer.commandFactories

import de.linkel.aoc.lib.computer.Command
import de.linkel.aoc.lib.computer.CommandFactory
import de.linkel.aoc.lib.computer.CommandLine

class LambdaCommandFactory(
    private val commands: Map<String, (args: List<String>) -> Command>
): CommandFactory {
    override fun create(commandLine: CommandLine): Command? {
        return commands[commandLine.command]?.let { it(commandLine.arguments) }
    }
}
