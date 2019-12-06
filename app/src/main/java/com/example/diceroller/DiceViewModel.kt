package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel(
    leftDice: SixSidedDice = SixSidedDice(),
    rightDice: SixSidedDice = SixSidedDice()
) : ViewModel() {

    val liveData = MutableLiveData(diceMap(leftDice, rightDice))

    fun next() {
        liveData.value = liveData.value.orEmpty()
            .map { it.key to it.value.next() }
            .toMap()
    }

    fun roll() {
        liveData.value = diceMap(SixSidedDice(), SixSidedDice())
    }

    fun reset() {
        liveData.value = emptyMap()
    }

    private fun diceMap(leftDice: SixSidedDice, rightDice: SixSidedDice) =
        mapOf(DiceKey.LEFT to leftDice, DiceKey.RIGHT to rightDice)
}

sealed class DiceKey {
    object LEFT : DiceKey()
    object RIGHT : DiceKey()
}

