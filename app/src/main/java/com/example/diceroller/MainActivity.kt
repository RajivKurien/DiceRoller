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
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var diceViewModel = DiceViewModel(ImageFinder(AndroidDiceImageResourceFetcher()))

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

    private fun updateDiceImages(event: DiceRollEvent) {
        return when (event) {
            is DiceRollEvent.Reset ->
                updateDiceDrawables(R.drawable.empty_dice, R.drawable.empty_dice)
            is DiceRollEvent.NewDice ->
                updateDiceDrawables(event.leftImage, event.rightImage)
        }
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

    private fun rollDice() {
        diceViewModel.roll()
    }

    private fun countUp() {
        diceViewModel.countUp()
    }

    private fun welcomeToast(context: Context) =
        Toast.makeText(context, daytimeMessage(), Toast.LENGTH_SHORT).show()

    private fun daytimeMessage(): String {
        val time = getCurrentHours()
        return when {
            time < 12 -> getString(R.string.good_morning)
            time < 18 -> getString(R.string.good_afternoon)
            else -> getString(R.string.good_evening)
        }
    }

    private fun getCurrentHours(): Int {
        return when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
                DateTimeFormatter
                    .ofPattern("HH")
                    .withZone(ZoneOffset.UTC)
                    .format(Instant.now())
                    .toInt()
            }
            else -> {
                Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
            }
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
}
