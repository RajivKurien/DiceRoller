package com.example.diceroller

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.diceroller.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }
        }

        welcomeToast(this.applicationContext)
    }

    private fun reset() {
        setLeftDiceImage(0)
        setRightDiceImage(0)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun welcomeToast(context: Context) =
        Toast.makeText(context, daytimeMessage(), Toast.LENGTH_SHORT).show()

    private fun rollDice() {
        setLeftDiceImage(Random.nextInt(6) + 1)
        setRightDiceImage(Random.nextInt(6) + 1)
    }

    private fun countUp() {
        setLeftDiceImage(nextDiceValue(currentDiceValue(binding.leftDiceImage)))
        setRightDiceImage(nextDiceValue(currentDiceValue(binding.rightDiceImage)))
    }

    private fun nextDiceValue(number: Int): Int = when {
        number < 6 -> number + 1
        else -> number
    }

    private fun setRightDiceImage(i: Int) = setDiceImage(i, binding.rightDiceImage)

    private fun setLeftDiceImage(i: Int) = setDiceImage(i, binding.leftDiceImage)

    private fun setDiceImage(i: Int, imageView: ImageView) {
        val drawableResource = diceImageResource(i)
        imageView.setImageResource(drawableResource)
        imageView.tag = drawableResource
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

    private fun currentDiceValue(imageView: ImageView): Int = when (imageView.tag) {
        R.drawable.dice_1 -> 1
        R.drawable.dice_2 -> 2
        R.drawable.dice_3 -> 3
        R.drawable.dice_4 -> 4
        R.drawable.dice_5 -> 5
        R.drawable.dice_6 -> 6
        else -> R.drawable.empty_dice
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun daytimeMessage(): String {

        val time = DateTimeFormatter
            .ofPattern("HH")
            .withZone(ZoneOffset.UTC)
            .format(Instant.now())
            .toInt()

        return "Good ${when {
            time < 12 -> getString(R.string.Morning)
            time < 18 -> getString(R.string.Afternoon)
            else -> getString(R.string.Evening)
        }}"
    }
}
