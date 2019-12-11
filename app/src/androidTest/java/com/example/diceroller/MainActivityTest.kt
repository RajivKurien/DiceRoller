package com.example.diceroller

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tappingRollDisplaysDiceImages() {
        rollButton().perform(click()) // roll dice and show images

        leftDice().check(notMatchesTag(R.drawable.empty_dice))
        rightDice().check(notMatchesTag(R.drawable.empty_dice))
    }

    @Test
    fun startsWithNoDice() {
        leftDice().check(matchesTag(R.drawable.empty_dice))
        rightDice().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun tappingResetDisplaysEmptyDiceImages() {
        resetButton().perform(click())

        leftDice().check(matchesTag(R.drawable.empty_dice))
        rightDice().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun countUpDoesNothingWhenNoDiceInitially() {
        countUpButton().perform(click())

        leftDice().check(matchesTag(R.drawable.empty_dice))
        rightDice().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun countUpDisplaysNextImage() {
        rollButton().perform(click())
        val expectedLeft = nextDice(getImageTag(leftDice()))
        val expectedRight = nextDice(getImageTag(rightDice()))

        countUpButton().perform(click())

        leftDice().check(matchesTag(expectedLeft))
        rightDice().check(matchesTag(expectedRight))
    }

    private fun leftDice() = onView(withId(R.id.left_dice_image))
    private fun rightDice() = onView(withId(R.id.right_dice_image))
    private fun rollButton() = onView(withId(R.id.roll_button))
    private fun resetButton() = onView(withId(R.id.reset_button))
    private fun countUpButton() = onView(withId(R.id.count_up_button))

    private fun nextDice(tag: Int): Int = when (tag) {
        R.drawable.dice_1 -> R.drawable.dice_2
        R.drawable.dice_2 -> R.drawable.dice_3
        R.drawable.dice_3 -> R.drawable.dice_4
        R.drawable.dice_4 -> R.drawable.dice_5
        R.drawable.dice_5 -> R.drawable.dice_6
        R.drawable.dice_6 -> R.drawable.dice_1
        else -> 0
    }
}
