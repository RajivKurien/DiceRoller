package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel(
    leftDie: SixSidedDie = SixSidedDie(),
    rightDie: SixSidedDie = SixSidedDie()
) :
    ViewModel() {

    val liveData = MutableLiveData(diceMap(leftDie, rightDie))

    fun next() {
        liveData.value = liveData.value.orEmpty()
            .map { it.key to it.value.next() }
            .toMap()
    }

    fun roll() {
        liveData.value = diceMap(SixSidedDie(), SixSidedDie())
    }

    private fun diceMap(leftDie: SixSidedDie, rightDie: SixSidedDie) =
        mapOf(DiceKey.LEFT to leftDie, DiceKey.RIGHT to rightDie)

    fun reset() {
        liveData.value = emptyMap()
    }
}

sealed class DiceKey {
    object LEFT : DiceKey()
    object RIGHT : DiceKey()
}

