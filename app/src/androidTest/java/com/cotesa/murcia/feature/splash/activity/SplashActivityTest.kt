package com.cotesa.murcia.feature.splash.activity



import android.content.Context
import android.content.SharedPreferences
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.test.core.app.ActivityScenario

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.BoundedMatcher
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.cotesa.common.util.Constant

import com.cotesa.murcia.R
import com.cotesa.murcia.adapter.CustomBeachListAdapter.BeachViewHolder
import com.google.android.exoplayer2.util.Assertions
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
/*
@LargeTest
@RunWith(AndroidJUnit4::class)
class SplashActivityTest {

    @RelaxedMockK
    private lateinit var preferencesMock : SharedPreferences

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var scenario: ActivityScenario<SplashActivity>

    private lateinit var context : Context


    @Before
    fun setup() {
        MockKAnnotations.init(this)

        context = InstrumentationRegistry.getInstrumentation().context

        scenario = ActivityScenario.launch(SplashActivity::class.java)
        scenario.moveToState(Lifecycle.State.STARTED)
    }

    @After
    fun dismount() {

    }

   @Test
    fun testBeachToFavorite() {

        // Given
        every { context.getSharedPreferences(Constant.USER_SETTINGS,0) } returns preferencesMock
        every { preferencesMock.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES,null)} returns mutableSetOf<String>()

        onView(withId(com.cotesa.common.R.id.mi_list)).perform(ViewActions.click())

        onView(withId(R.id.rv_listRecycle)).perform(
            RecyclerViewActions.actionOnItemAtPosition<BeachViewHolder>(
                0,
                ViewActions.longClick()
            )
        )

        onView(withId(R.id.rv_listRecycle)).check(

        )
    }



    }*/
