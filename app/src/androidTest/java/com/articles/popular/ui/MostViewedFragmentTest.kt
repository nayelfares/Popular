package com.articles.popular.ui

import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.articles.popular.R
import com.articles.popular.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.lang.Thread.sleep

@ExperimentalCoroutinesApi
@HiltAndroidTest
@Suppress("IllegalIdentifier")
class MostViewedFragmentTest{
    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        launchFragmentInHiltContainer<MostViewedFragment> {}
    }

    @Test
    fun `Test Recyclerview visibility`(){
             onView(withId(R.id.articles_recycler_view))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun `test recyclerview scrolling`(){
        //waiting data fetch
        sleep(3000)
        for (i in 0..3) {
            sleep(500)
            onView(withId(R.id.articles_recycler_view))
                .perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(i))
        }
    }


}