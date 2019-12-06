package com.example.diceroller

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
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
        onView(withId(R.id.reset_button)).perform(click())
        val viewInteraction: ViewInteraction = onView(withId(R.id.left_dice_image))

//        viewInteraction.check(matches(DrawableMatcher(R.drawable.empty_dice)))
//        viewInteraction.check(matches(withTagKey(R.drawable.empty_dice)))
        viewInteraction.check(matches(TagMatcher(R.drawable.empty_dice)))
    }
}

class TagMatcher(private val expectedTag: Int) : TypeSafeMatcher<View?>(View::class.java) {
    override fun matchesSafely(item: View?): Boolean {
        return item?.tag == expectedTag
    }

    override fun describeTo(description: Description?) {
        description?.appendText("with tag from resource id: ")
        description?.appendValue(expectedTag)
    }
}

class DrawableMatcher internal constructor(private val expectedId: Int) :
    TypeSafeMatcher<View?>(View::class.java) {
    private var resourceName: String? = null

    override fun matchesSafely(target: View?): Boolean {
        if (target !is ImageView) {
            return false
        }
        when (expectedId) {
            EMPTY -> {
                return target.drawable == null
            }
            ANY -> {
                return target.drawable != null
            }
            else -> {
                val resources: Resources = target.getContext().resources
                val expectedDrawable: Drawable = resources.getDrawable(expectedId, null)
                resourceName = resources.getResourceEntryName(expectedId)
                val bitmap = getBitmap(target.drawable)
                val otherBitmap = getBitmap(expectedDrawable)
                return bitmap.sameAs(otherBitmap)
            }
        }
    }

    private fun getBitmap(drawable: Drawable): Bitmap {
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    override fun describeTo(description: Description) {
        description.appendText("with drawable from resource id: ")
        description.appendValue(expectedId)
        if (resourceName != null) {
            description.appendText("[")
            description.appendText(resourceName)
            description.appendText("]")
        }
    }

    companion object {
        const val EMPTY = -1
        const val ANY = -2
    }

}
