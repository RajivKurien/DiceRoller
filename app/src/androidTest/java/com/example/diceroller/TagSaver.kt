package com.example.diceroller

import android.view.View
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

fun getImageTag(view: ViewInteraction): Int {
    val tagSaver = object : TypeSafeMatcher<View>() {
        var tag: Int = 0

        override fun describeTo(description: Description?) {
            description?.appendText("Getting current image tag")
        }

        override fun matchesSafely(view: View?): Boolean {
            tag = view?.tag as Int
            return true
        }
    }

    view.check(ViewAssertions.matches(tagSaver))
    return tagSaver.tag
}
