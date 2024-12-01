package de.linkel.aoc.utils.math

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.BigInteger

class MathTest {
    @Test
    fun `lcm 1 and 3 is 3`() {
        Assertions.assertThat(lcm(1, 3)).isEqualTo(3)
        Assertions.assertThat(lcm(1L, 3L)).isEqualTo(3L)
        Assertions.assertThat(lcm(BigDecimal.ONE, BigDecimal("3"))).isEqualByComparingTo(BigDecimal("3"))
        Assertions.assertThat(lcm(BigInteger.ONE, BigInteger("3"))).isEqualByComparingTo(BigInteger("3"))
    }

    @Test
    fun `lcm 21 and 77 is 231`() {
        Assertions.assertThat(lcm(21, 77)).isEqualTo(231)
        Assertions.assertThat(lcm(21L, 77L)).isEqualTo(231L)
        Assertions.assertThat(lcm(BigDecimal("21"), BigDecimal("77"))).isEqualByComparingTo(BigDecimal("231"))
        Assertions.assertThat(lcm(BigInteger("21"), BigInteger("77"))).isEqualByComparingTo(BigInteger("231"))
    }

    @Test
    fun `lcm -21 and 77 is 231`() {
        Assertions.assertThat(lcm(-21, 77)).isEqualTo(231)
        Assertions.assertThat(lcm(-21L, 77L)).isEqualTo(231L)
        Assertions.assertThat(lcm(BigDecimal("-21"), BigDecimal("77"))).isEqualByComparingTo(BigDecimal("231"))
        Assertions.assertThat(lcm(BigInteger("-21"), BigInteger("77"))).isEqualByComparingTo(BigInteger("231"))
    }

    @Test
    fun `lcm 2 and 0 throws Exception`() {
        Assertions.assertThatThrownBy { lcm(2, 0) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { lcm(2L, 0L) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { lcm(BigDecimal("2"), BigDecimal.ZERO) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { lcm(BigInteger("2"), BigInteger.ZERO) }.isInstanceOf(ArithmeticException::class.java)
    }

    @Test
    fun `gcd 1 and 3 is 1`() {
        Assertions.assertThat(gcd(1, 3)).isEqualTo(1)
        Assertions.assertThat(gcd(1L, 3L)).isEqualTo(1L)
        Assertions.assertThat(gcd(BigDecimal.ONE, BigDecimal("3"))).isEqualByComparingTo(BigDecimal.ONE)
        Assertions.assertThat(gcd(BigInteger.ONE, BigInteger("3"))).isEqualByComparingTo(BigInteger.ONE)
    }

    @Test
    fun `gcd 2 and 0 throws Exception`() {
        Assertions.assertThatThrownBy { gcd(2, 0) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { gcd(2L, 0L) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { gcd(BigDecimal("2"), BigDecimal.ZERO) }.isInstanceOf(ArithmeticException::class.java)
        Assertions.assertThatThrownBy { gcd(BigInteger("2"), BigInteger.ZERO) }.isInstanceOf(ArithmeticException::class.java)
    }

    @Test
    fun `gcd 48 and 332 is 4`() {
        Assertions.assertThat(gcd(48, 332)).isEqualTo(4)
        Assertions.assertThat(gcd(48L, 332)).isEqualTo(4L)
        Assertions.assertThat(gcd(BigDecimal("48"), BigDecimal("332"))).isEqualByComparingTo(BigDecimal("4"))
        Assertions.assertThat(gcd(BigInteger("48"), BigInteger("332"))).isEqualByComparingTo(BigInteger("4"))
    }

    @Test
    fun `gcd 48 and -332 is 4`() {
        Assertions.assertThat(gcd(48, -332)).isEqualTo(4)
        Assertions.assertThat(gcd(48L, -332L)).isEqualTo(4L)
        Assertions.assertThat(gcd(BigDecimal("48"), BigDecimal("-332"))).isEqualByComparingTo(BigDecimal("4"))
        Assertions.assertThat(gcd(BigInteger("48"), BigInteger("-332"))).isEqualByComparingTo(BigInteger("4"))
    }

    @Test
    fun `primes under 100`() {
        Assertions.assertThat(primes().take(50).filter { it < 100}.toList()).isEqualTo(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97))
    }
}
