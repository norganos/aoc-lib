package de.linkel.aoc.lib.computer

interface CommandContext {
    fun getEnv(name: String): String
}
