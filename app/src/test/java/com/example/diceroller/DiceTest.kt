package com.example.diceroller

import com.example.diceroller.Dice.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.random.Random

class DiceTest {

    @Test
    fun `next increments die value by one`() {
        assertEquals(Dice.countUp(ONE), TWO)
        assertEquals(Dice.countUp(TWO), THREE)
        assertEquals(Dice.countUp(THREE), FOUR)
        assertEquals(Dice.countUp(FOUR), FIVE)
        assertEquals(Dice.countUp(FIVE), SIX)
        assertEquals(Dice.countUp(SIX), ONE)
    }

    @Test
    fun `random value for new die`() {
        assertEquals(SIX, Dice.randomDice(Random(5)))
        assertEquals(FOUR, Dice.randomDice(Random(1)))
    }
}
