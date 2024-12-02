package de.linkel.aoc.utils.geometry.plain

import de.linkel.aoc.utils.geometry.plain.continuous.ContinuousPlainGeometryBuilder
import de.linkel.aoc.utils.geometry.plain.discrete.DiscretePlainGeometryBuilder

@Suppress("unused")
class PlainGeometryBuilder {
    fun discrete() = DiscretePlainGeometryBuilder()
    fun continuous() = ContinuousPlainGeometryBuilder()
}