package com.articles.popular

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.contrib.DrawerActions
import androidx.test.espresso.contrib.NavigationViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("IllegalIdentifier")
@RunWith(AndroidJUnit4::class)
class MainActivityTest{
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test fun `Launch main activity test`() {
        activityScenarioRule.scenario
    }
    @Test
    fun `Navigation test`() {
        onView(withId(R.id.drawer_layout)).perform(DrawerActions.open())
        onView(withId(R.id.nav_view)).perform(NavigationViewActions.navigateTo(R.id.most_viewed))
    }
}