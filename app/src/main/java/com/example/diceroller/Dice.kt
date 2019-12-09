package com.example.diceroller

import kotlin.random.Random

enum class Dice(val value: Int) {
    ONE(1), TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6);

    companion object {

        private val MAX_DICE_VALUE = values().size

        fun randomDice(random: Random = Random.Default): Dice {
            val value = random.nextInt(MAX_DICE_VALUE) + 1
            return values().first { it.value == value }
        }

        fun countUp(dice: Dice): Dice {
            val nextDiceValue: Any = dice.value % MAX_DICE_VALUE + 1
            return values().first { it.value == nextDiceValue }
        }
    }
}
