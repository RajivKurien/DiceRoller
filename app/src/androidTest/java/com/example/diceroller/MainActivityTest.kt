package com.example.diceroller

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.diceroller.R.drawable.*
import com.example.diceroller.R.id.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun startsWithNoDice() {
        leftDice().check(matchesTag(empty_dice))
        rightDice().check(matchesTag(empty_dice))
    }

    @Test
    fun tappingRollDisplaysDiceImages() {
        rollButton().perform(click()) // roll dice and show images

        leftDice().check(notMatchesTag(empty_dice))
        rightDice().check(notMatchesTag(empty_dice))
    }

    @Test
    fun tappingResetDisplaysEmptyDiceImages() {
        resetButton().perform(click())

        leftDice().check(matchesTag(empty_dice))
        rightDice().check(matchesTag(empty_dice))
    }

    @Test
    fun countUpDoesNothingWhenNoDiceInitially() {
        countUpButton().perform(click())

        leftDice().check(matchesTag(empty_dice))
        rightDice().check(matchesTag(empty_dice))
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

    private fun leftDice() = onView(withId(left_dice_image))
    private fun rightDice() = onView(withId(right_dice_image))
    private fun rollButton() = onView(withId(roll_button))
    private fun resetButton() = onView(withId(reset_button))
    private fun countUpButton() = onView(withId(count_up_button))

    private fun nextDice(tag: Int): Int = when (tag) {
        dice_1 -> dice_2
        dice_2 -> dice_3
        dice_3 -> dice_4
        dice_4 -> dice_5
        dice_5 -> dice_6
        dice_6 -> dice_1
        else -> 0
    }
}
