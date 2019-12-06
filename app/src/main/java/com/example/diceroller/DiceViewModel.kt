package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel(
    private val imageFinder: ImageFinder,
    leftDice: SixSidedDice = SixSidedDice(),
    rightDice: SixSidedDice = SixSidedDice()
) : ViewModel() {

    val liveData: MutableLiveData<DiceRollerEvent> =
        MutableLiveData(
            DiceRollerEvent.NewDice(
                twoDice = TwoDice(leftDice, rightDice),
                leftImage = imageFinder.getDiceImage(leftDice.value),
                rightImage = imageFinder.getDiceImage(rightDice.value)
            )
        )

    fun roll() {
        val leftDice = SixSidedDice()
        val rightDice = SixSidedDice()

        val diceRollerEvent = DiceRollerEvent.NewDice(
            twoDice = TwoDice(leftDice, rightDice),
            leftImage = imageFinder.getDiceImage(leftDice.value),
            rightImage = imageFinder.getDiceImage(rightDice.value)
        )

        liveData.postValue(diceRollerEvent)
    }

    fun reset() {
        liveData.postValue(DiceRollerEvent.Reset)
    }

    fun countUp() {
        when (val currentEvent = liveData.value) {
            is DiceRollerEvent.NewDice -> {
                val leftDice = currentEvent.twoDice.leftDice.countUp()
                val rightDice = currentEvent.twoDice.rightDice.countUp()

                val diceRollerEvent = DiceRollerEvent.NewDice(
                    twoDice = TwoDice(leftDice, rightDice),
                    leftImage = imageFinder.getDiceImage(leftDice.value),
                    rightImage = imageFinder.getDiceImage(rightDice.value)
                )

                liveData.postValue(diceRollerEvent)
            }
        }
    }
}

sealed class DiceRollerEvent {
    data class NewDice(val twoDice: TwoDice, val leftImage: Int, val rightImage: Int) :
        DiceRollerEvent()

    object Reset : DiceRollerEvent()
}

data class TwoDice(val leftDice: SixSidedDice, val rightDice: SixSidedDice)

