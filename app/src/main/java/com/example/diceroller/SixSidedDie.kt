package com.example.diceroller

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class SixSidedDie(value: Int = 0) : ViewModel() {

    private val diceValue: MutableLiveData<Int> = MutableLiveData(value)

    fun value(): LiveData<Int> = diceValue

    fun next(): LiveData<Int> {
        return when {
            diceValue.value!! < 6 -> {
                diceValue.value = diceValue.value!! + 1
                diceValue
            }
            else -> diceValue
        }
    }

    fun roll(): LiveData<Int> {
        diceValue.value = Random.nextInt(6) + 1
        return diceValue
    }

    fun reset(): LiveData<Int> {
        diceValue.value = 0
        return diceValue
    }

}
