package com.example.diceroller

import androidx.annotation.DrawableRes
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


data class DiceEvent(val list: List<DiceModel> = emptyList())

class DiceViewModel(
    private val imageFinder: ImageFinder,
    val liveData: MutableLiveData<DiceEvent> = MutableLiveData(DiceEvent())
) : ViewModel() {

    fun roll() = liveData.postValue(newSetOfNineDice())

    private fun newSetOfNineDice(): DiceEvent {
        return DiceEvent((0..9).map {
            val dice = Dice.randomDice()
            DiceModel(it, dice, imageFinder.getDiceImage(dice))
        }.toList())
    }

    fun reset() {
        liveData.postValue(DiceEvent(emptyList()))
    }

    fun countUp() {
        when (val event = liveData.value) {
            is DiceEvent -> liveData.postValue(
                DiceEvent((event).list
                    .map {
                        val dice = Dice.countUp(it.dice)
                        DiceModel(it.id, dice, imageFinder.getDiceImage(dice))
                    }
                ))
        }
    }

    fun rollDice(position: Int) {
        val dice = Dice.randomDice()
        val newList = (liveData.value?.list ?: emptyList()).toMutableList()
        newList[position] = DiceModel(position, dice, imageFinder.getDiceImage(dice))
        liveData.postValue(DiceEvent(newList))
    }
}

data class DiceModel(val id: Int, val dice: Dice, @DrawableRes val image: Int)
