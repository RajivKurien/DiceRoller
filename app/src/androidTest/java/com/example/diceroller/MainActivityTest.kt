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
    fun startsWithNoDice() {
        leftDiceImage().check(matchesTag(R.drawable.empty_dice))
        rightDiceImage().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun tappingRollDisplaysDiceImages() {
        onView(withId(R.id.roll_button)).perform(click()) // roll dice and show images

        leftDiceImage().check(notMatchesTag(R.drawable.empty_dice))
        rightDiceImage().check(notMatchesTag(R.drawable.empty_dice))
    }

    @Test
    fun tappingResetDisplaysEmptyDiceImages() {
        onView(withId(R.id.reset_button)).perform(click())

        leftDiceImage().check(matchesTag(R.drawable.empty_dice))
        rightDiceImage().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun countUpDoesNothingWhenNoDiceInitially() {
        onView(withId(R.id.count_up_button)).perform(click())

        leftDiceImage().check(matchesTag(R.drawable.empty_dice))
        rightDiceImage().check(matchesTag(R.drawable.empty_dice))
    }

    @Test
    fun countUpDisplaysNextImage() {
        onView(withId(R.id.roll_button)).perform(click()) // roll dice and show images
        val expectedLeft = nextDice(getImageTag(leftDiceImage()))
        val expectedRight = nextDice(getImageTag(rightDiceImage()))

        onView(withId(R.id.count_up_button)).perform(click())

        leftDiceImage().check(matchesTag(expectedLeft))
        rightDiceImage().check(matchesTag(expectedRight))
    }

    private fun rightDiceImage() = onView(withId(R.id.right_dice_image))
    private fun leftDiceImage() = onView(withId(R.id.left_dice_image))

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

