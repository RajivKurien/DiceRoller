package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel(
    private val imageFinder: ImageFinder,
    leftDice: Dice = Dice.randomDice(),
    rightDice: Dice = Dice.randomDice()
) : ViewModel() {

    val liveData: MutableLiveData<DiceRollerEvent> =
        MutableLiveData(
            DiceRollerEvent.NewDice(
                twoDice = TwoDice(leftDice, rightDice),
                leftImage = imageFinder.getDiceImage(leftDice),
                rightImage = imageFinder.getDiceImage(rightDice)
            )
        )

    fun roll() {
        val leftDice = Dice.randomDice()
        val rightDice = Dice.randomDice()

        val diceRollerEvent = DiceRollerEvent.NewDice(
            twoDice = TwoDice(leftDice, rightDice),
            leftImage = imageFinder.getDiceImage(leftDice),
            rightImage = imageFinder.getDiceImage(rightDice)
        )

        liveData.postValue(diceRollerEvent)
    }

    fun reset() {
        liveData.postValue(DiceRollerEvent.Reset)
    }

    fun countUp() {
        when (val currentEvent = liveData.value) {
            is DiceRollerEvent.NewDice -> {
                val leftDice = Dice.countUp(currentEvent.twoDice.leftDice)
                val rightDice = Dice.countUp(currentEvent.twoDice.rightDice)

                val diceRollerEvent = DiceRollerEvent.NewDice(
                    twoDice = TwoDice(leftDice, rightDice),
                    leftImage = imageFinder.getDiceImage(leftDice),
                    rightImage = imageFinder.getDiceImage(rightDice)
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

data class TwoDice(val leftDice: Dice, val rightDice: Dice)
