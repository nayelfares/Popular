package com.articles.popular

import android.util.Log
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("IllegalIdentifier")
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MainActivityTest{
    @get:Rule
    var hiltRule = HiltAndroidRule(this)
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test fun `Launch main activity test`() {
        Log.e("start","start")
        activityScenarioRule.scenario
        Log.e("end","end")
    }
    @Test
    fun `Navigation test`() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.most_viewed))
    }
}