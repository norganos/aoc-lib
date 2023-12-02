package de.linkel.aoc.lib.computer.commandFactories

import de.linkel.aoc.lib.computer.Command
import de.linkel.aoc.lib.computer.CommandFactory
import de.linkel.aoc.lib.computer.CommandLine

@Suppress("unused")
class DelegatingCommandFactory(
    private val factories: List<CommandFactory>
): CommandFactory {
    override fun create(commandLine: CommandLine): Command? {
        return factories.firstNotNullOfOrNull { it.create(commandLine) }
    }
}
