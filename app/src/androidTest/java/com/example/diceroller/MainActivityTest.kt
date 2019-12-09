package com.example.diceroller

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {

    @get:Rule
    var activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun tappingResetDisplaysEmptyDiceImages() {
        onView(withId(R.id.roll_button)).perform(click()) // roll dice and show images
        onView(withId(R.id.reset_button)).perform(click()) // press reset
        onView(withId(R.id.left_dice_image)).check(matches(TagMatcher(R.drawable.empty_dice)))
        onView(withId(R.id.right_dice_image)).check(matches(TagMatcher(R.drawable.empty_dice)))
    }

    @Test
    fun countUpDisplaysNextImage() {
        val currentLeftImage = getCurrentImageTag(onView(withId(R.id.left_dice_image)))
        val currentRightImage = getCurrentImageTag(onView(withId(R.id.right_dice_image)))

        onView(withId(R.id.count_up_button)).perform(click())

        val expectedLeftImage = getNextImageTag(currentLeftImage)
        val expectedRightImage = getNextImageTag(currentRightImage)
        onView(withId(R.id.left_dice_image)).check(matches(TagMatcher(expectedLeftImage)))
        onView(withId(R.id.right_dice_image)).check(matches(TagMatcher(expectedRightImage)))
    }

    private fun getNextImageTag(tag: Int): Int = when (tag) {
        R.drawable.dice_1 -> R.drawable.dice_2
        R.drawable.dice_2 -> R.drawable.dice_3
        R.drawable.dice_3 -> R.drawable.dice_4
        R.drawable.dice_4 -> R.drawable.dice_5
        R.drawable.dice_5 -> R.drawable.dice_6
        R.drawable.dice_6 -> R.drawable.dice_1
        else -> 0
    }

    private fun getCurrentImageTag(onView: ViewInteraction): Int {
        val currentImage = TagSaver()
        onView.check(matches(currentImage))
        return currentImage.diceTag
    }


}

class TagSaver : TypeSafeMatcher<View>() {
    var diceTag: Int = 0

    override fun describeTo(description: Description?) {
        description?.appendText("Getting current image tag")
    }

    override fun matchesSafely(view: View?): Boolean {
        diceTag = view?.tag as Int
        return true
    }
}

class TagMatcher(private val expectedTag: Int) : TypeSafeMatcher<View?>(View::class.java) {
    override fun matchesSafely(view: View?): Boolean {
        return view?.tag == expectedTag
    }

    override fun describeTo(description: Description?) {
        description?.appendText("with tag from resource id: ")
        description?.appendValue(expectedTag)
    }
}
