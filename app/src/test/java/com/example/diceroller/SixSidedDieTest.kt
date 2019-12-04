package com.example.diceroller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import kotlin.random.Random

class SixSidedDieTest {

    @ParameterizedTest
    @CsvSource(
        "1, 2",
        "2, 3",
        "3, 4",
        "4, 5",
        "5, 6",
        "6, 1"
    )
    fun `next increments die value by one`(initial: Int, expected: Int) {
        val actualDie = SixSidedDie(initial).next()

        assertEquals(SixSidedDie(expected), actualDie)
    }

    @Test
    fun `face value less than one throws exception`() {
        assertThrows<IllegalArgumentException> { SixSidedDie(0) }
    }

    @Test
    fun `face value greater than six throws exception`() {
        assertThrows<IllegalArgumentException> { SixSidedDie(7) }
    }

    @Test
    fun `random value for new die`() {
        assertEquals(SixSidedDie(6), SixSidedDie(Random(5)))
        assertEquals(SixSidedDie(4), SixSidedDie(Random(1)))
    }
}
