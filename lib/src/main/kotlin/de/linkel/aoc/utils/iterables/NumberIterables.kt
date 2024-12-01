package de.linkel.aoc.utils.iterables

import de.linkel.aoc.utils.math.CommonMath
import java.math.BigDecimal

fun Iterable<BigDecimal>.sum() = this.reduce(BigDecimal::plus)
fun Sequence<BigDecimal>.sum(): BigDecimal = this.reduce(BigDecimal::times)

fun Iterable<BigDecimal>.product() = this.reduce(BigDecimal::times)
fun Sequence<BigDecimal>.product() = this.reduce(BigDecimal::times)
fun Iterable<Int>.product() = this.reduce(Int::times)
fun Sequence<Int>.product() = this.reduce(Int::times)
fun Iterable<Long>.product() = this.reduce(Long::times)
fun Sequence<Long>.product() = this.reduce(Long::times)

fun Iterable<BigDecimal>.lcm() = this.reduce(CommonMath::lcm)
fun Sequence<BigDecimal>.lcm() = this.reduce(CommonMath::lcm)
fun Iterable<Int>.lcm() = this.reduce(CommonMath::lcm)
fun Sequence<Int>.lcm() = this.reduce(CommonMath::lcm)
fun Iterable<Long>.lcm() = this.reduce(CommonMath::lcm)
fun Sequence<Long>.lcm() = this.reduce(CommonMath::lcm)

