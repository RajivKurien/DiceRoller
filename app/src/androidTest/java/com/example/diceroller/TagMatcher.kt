package com.example.diceroller

import android.view.View
import androidx.test.espresso.ViewAssertion
import androidx.test.espresso.assertion.ViewAssertions.matches
import org.hamcrest.Description
import org.hamcrest.TypeSafeMatcher

class TagMatcher(private val expectedTag: Int) :
    TypeSafeMatcher<View?>(View::class.java) {
    override fun matchesSafely(view: View?): Boolean {
        return view?.tag == expectedTag
    }

    override fun describeTo(description: Description?) {
        description?.appendText("with tag from resource id: ")
        description?.appendValue(expectedTag)
    }
}

class NotTagMatcher(private val expectedTag: Int) :
    TypeSafeMatcher<View?>(View::class.java) {
    override fun matchesSafely(view: View?): Boolean {
        return !TagMatcher(expectedTag).matches(view)
    }

    override fun describeTo(description: Description?) {
        description?.appendText("not with tag from resource id: ")
        description?.appendValue(expectedTag)
    }
}

fun matchesTag(tag: Int): ViewAssertion = matches(TagMatcher(tag))
fun notMatchesTag(tag: Int): ViewAssertion = matches(NotTagMatcher(tag))
