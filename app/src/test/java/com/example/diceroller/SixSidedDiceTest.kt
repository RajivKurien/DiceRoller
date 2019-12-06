package com.example.diceroller

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class SixSidedDiceTest {

    @Test
    fun `next increments die value by one`() {
        Dice.values().forEach {
            val actualDie = SixSidedDice(it).next()
            assertEquals(SixSidedDice(Dice.nextFrom(it)), actualDie)
        }
    }

    @Test
    fun `random value for new die`() {
        assertEquals(Dice.SIX, Dice.randomDice(Random(5)))
        assertEquals(Dice.FOUR, Dice.randomDice(Random(1)))
    }
}
