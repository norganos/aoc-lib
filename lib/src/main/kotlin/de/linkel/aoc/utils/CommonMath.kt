package de.linkel.aoc.utils

import java.math.BigDecimal
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

class CommonMath {
    companion object {
        fun lcm(a: Int, b: Int): Int {
            if (a == 0 || b == 0) throw ArithmeticException("could not find LCM for $a and $b")
            val max = max(abs(a), abs(b))
            val min = min(abs(a), abs(b))
            return generateSequence(max) { it + max }
                .first { it % min == 0 }
        }

        fun gcd(a: Int, b: Int): Int {
            if (a == 0 || b == 0) throw ArithmeticException("could not find GCD for $a and $b")
            return (min(abs(a), abs(b)) downTo 1).first { f ->
                a % f == 0 && b % f == 0
            }
        }

        fun lcm(a: Long, b: Long): Long {
            if (a == 0L || b == 0L) throw ArithmeticException("could not find LCM for $a and $b")
            val max = max(abs(a), abs(b))
            val min = min(abs(a), abs(b))
            return generateSequence(max) { it + max }
                .first { it % min == 0L }
        }

        fun gcd(a: Long, b: Long): Long {
            if (a == 0L || b == 0L) throw ArithmeticException("could not find GCD for $a and $b")
            return (min(abs(a), abs(b)) downTo 1L).first { f ->
                a % f == 0L && b % f == 0L
            }
        }

        fun lcm(a: BigDecimal, b: BigDecimal): BigDecimal {
            if (a.isZero() || b.isZero()) throw ArithmeticException("could not find LCM for $a and $b")
            val max = a.abs().max(b.abs())
            val min = a.abs().min(b.abs())
            return generateSequence(max) { it + max }
                .first { (it % min).isZero() }
        }

        fun gcd(a: BigDecimal, b: BigDecimal): BigDecimal {
            if (a.isZero() || b.isZero()) throw ArithmeticException("could not find GCD for $a and $b")
            return generateSequence(a.abs().min(b.abs())) { if (it > BigDecimal.ONE) it - BigDecimal.ONE else null }
                .first { f ->
                    (a % f).isZero() && (b % f).isZero()
                }
        }

        fun lcm(a: BigInteger, b: BigInteger): BigInteger {
            if (a.isZero() || b.isZero()) throw ArithmeticException("could not find LCM for $a and $b")
            val max = a.abs().max(b.abs())
            val min = a.abs().min(b.abs())
            return generateSequence(max) { it + max }
                .first { (it % min).isZero() }
        }

        fun gcd(a: BigInteger, b: BigInteger): BigInteger {
            if (a.isZero() || b.isZero()) throw ArithmeticException("could not find GCD for $a and $b")
            return generateSequence(a.abs().min(b.abs())) { if (it > BigInteger.ONE) it - BigInteger.ONE else null }
                .first { f ->
                    (a % f).isZero() && (b % f).isZero()
                }
        }
    }
}

fun BigDecimal.isZero(): Boolean = this.compareTo(BigDecimal.ZERO) == 0
fun BigInteger.isZero(): Boolean = this.compareTo(BigInteger.ZERO) == 0

fun lcm(a: Int, b: Int) = CommonMath.lcm(a, b)
fun lcm(a: Long, b: Long) = CommonMath.lcm(a, b)
fun lcm(a: BigDecimal, b: BigDecimal) = CommonMath.lcm(a, b)
fun lcm(a: BigInteger, b: BigInteger) = CommonMath.lcm(a, b)
fun gcd(a: Int, b: Int) = CommonMath.gcd(a, b)
fun gcd(a: Long, b: Long) = CommonMath.gcd(a, b)
fun gcd(a: BigDecimal, b: BigDecimal) = CommonMath.gcd(a, b)
fun gcd(a: BigInteger, b: BigInteger) = CommonMath.gcd(a, b)

fun primes(): Sequence<Int> {
    return sequence {
        yield(2)
        val sieve = mutableSetOf(2)
        var num = 3
        while (true) {
            if (sieve.none { num % it == 0 }) {
                yield(num)
                sieve.add(num)
            }
            num += 2
        }
    }
}
