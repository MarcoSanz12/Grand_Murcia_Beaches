package com.cotesa.murcia.feature.home.fragment

import android.content.Context
import android.content.SharedPreferences
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.recyclerview.widget.RecyclerView
import com.cotesa.common.entity.beach.Beach
import com.cotesa.common.util.Constant
import com.cotesa.murcia.adapter.CustomBeachListAdapter
import com.cotesa.murcia.feature.home.viewmodel.HomeViewModel
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ListFragmentTest{

/*
    @MockK
    private lateinit var homeViewModel: HomeViewModel

    @RelaxedMockK
    private lateinit var preferencesMock : SharedPreferences

    @MockK
    private lateinit var contextMock : Context

    @MockK
    private lateinit var recyclerViewMock : RecyclerView

    @MockK
    private lateinit var adapterMock : CustomBeachListAdapter

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun onBefore(){
        MockKAnnotations.init(this)

    }
    @After
    fun onAfter(){

    }

    // UpdateFavorites

    @Test
    fun `when saving non previously saved beaches VERIFY if they are saved`(){

        // Given
        every { contextMock.getSharedPreferences(Constant.USER_SETTINGS,0) } returns preferencesMock

        every { recyclerViewMock.adapter } returns adapterMock

        // When

        val beach1 = Beach().apply { nid = 0
            title = "Beach 1"}
        val beach2 = Beach().apply { nid = 1
            title = "Beach 2"}
        val beach3 = Beach().apply { nid = 2
            title = "Beach 3"}


    }*/

}