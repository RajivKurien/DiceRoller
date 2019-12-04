package com.example.diceroller

import kotlin.random.Random

data class SixSidedDie(val value: Int) {

    init {
        when {
            value < 1 -> throw IllegalArgumentException("Value $value was less than one")
            value > 6 -> throw IllegalArgumentException("Value $value was greater than six")
        }
    }

    constructor(random: Random = Random.Default) : this(random.nextInt(6) + 1)

    fun next(): SixSidedDie = SixSidedDie(value % 6 + 1)
}