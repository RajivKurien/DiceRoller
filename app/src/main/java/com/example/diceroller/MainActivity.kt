package com.example.diceroller

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.diceroller.databinding.ActivityMainBinding
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val resourceFinder = ImageFinder(AndroidDiceImageResourceFetcher())
    private var diceViewModel = DiceViewModel(resourceFinder)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            rollButton.setOnClickListener { rollDice() }
            countUpButton.setOnClickListener { countUp() }
            resetButton.setOnClickListener { reset() }
        }

        diceViewModel.liveData.observe(this, Observer { updateDiceImages(it) })

        welcomeToast(this.applicationContext)
    }

    private fun updateDiceImages(diceRollerEvent: DiceRollerEvent) =
        when (val event = diceRollerEvent) {
            is DiceRollerEvent.Reset ->
                updateDiceDrawables(R.drawable.empty_dice, R.drawable.empty_dice)
            is DiceRollerEvent.NewDice ->
                updateDiceDrawables(event.leftImage, event.rightImage)
        }

    private fun updateDiceDrawables(
        @DrawableRes leftDrawable: Int,
        @DrawableRes rightDrawable: Int
    ) {
        binding.leftDiceImage.apply {
            setImageResource(leftDrawable)
            tag = leftDrawable
        }
        binding.rightDiceImage.apply {
            setImageResource(rightDrawable)
            tag = rightDrawable
        }
    }

    private fun reset() {
        diceViewModel.reset()
    }

    private fun welcomeToast(context: Context) =
        Toast.makeText(context, daytimeMessage(), Toast.LENGTH_SHORT).show()

    private fun rollDice() {
        diceViewModel.roll()
    }

    private fun countUp() {
        diceViewModel.countUp()
    }

    private fun daytimeMessage(): String {
        val time = getCurrentHours()
        return "Good ${when {
            time < 12 -> getString(R.string.Morning)
            time < 18 -> getString(R.string.Afternoon)
            else -> getString(R.string.Evening)
        }}"
    }

    private fun getCurrentHours(): Int {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter
                .ofPattern("HH")
                .withZone(ZoneOffset.UTC)
                .format(Instant.now())
                .toInt()
        } else {
            TODO("VERSION.SDK_INT < O")
        }
    }

}

class AndroidDiceImageResourceFetcher : ResourceFetcher {
    override fun fetchOne() = R.drawable.dice_1
    override fun fetchTwo() = R.drawable.dice_2
    override fun fetchThree() = R.drawable.dice_3
    override fun fetchFour() = R.drawable.dice_4
    override fun fetchFive() = R.drawable.dice_5
    override fun fetchSix() = R.drawable.dice_6
    override fun fetchEmpty() = R.drawable.empty_dice
}
