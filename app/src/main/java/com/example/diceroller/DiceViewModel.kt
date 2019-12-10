package com.example.diceroller

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DiceViewModel(
    private val imageFinder: ImageFinder,
    val liveData: MutableLiveData<DiceRollEvent> = MutableLiveData(DiceRollEvent.Reset)
) : ViewModel() {

    fun roll() {
        liveData.postValue(newDice(Dice.randomDice(), Dice.randomDice()))
    }

    fun reset() {
        liveData.postValue(DiceRollEvent.Reset)
    }

    fun countUp() {
        when (val event = liveData.value) {
            is DiceRollEvent.NewDice -> {
                event.twoDice.apply {
                    liveData.postValue(newDice(Dice.countUp(leftDice), Dice.countUp(rightDice)))
                }
            }
        }
    }

    private fun newDice(leftDice: Dice, rightDice: Dice): DiceRollEvent.NewDice {
        return DiceRollEvent.NewDice(
            twoDice = TwoDice(leftDice, rightDice),
            leftImage = imageFinder.getDiceImage(leftDice),
            rightImage = imageFinder.getDiceImage(rightDice)
        )
    }
}

sealed class DiceRollEvent {
    data class NewDice(val twoDice: TwoDice, val leftImage: Int, val rightImage: Int) :
        DiceRollEvent()

    object Reset : DiceRollEvent()
}

data class TwoDice(val leftDice: Dice, val rightDice: Dice)
