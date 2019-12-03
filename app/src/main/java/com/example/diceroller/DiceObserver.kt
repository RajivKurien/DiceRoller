package com.example.diceroller

import android.widget.ImageView
import androidx.lifecycle.Observer

class DiceObserver(private var diceImage: ImageView) : Observer<Int> {

    override fun onChanged(t: Int?) {
        t?.let { diceImage.setImageResource(diceImageResource(it)) }
    }

    private fun diceImageResource(i: Int): Int = when (i) {
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.empty_dice
    }
}
