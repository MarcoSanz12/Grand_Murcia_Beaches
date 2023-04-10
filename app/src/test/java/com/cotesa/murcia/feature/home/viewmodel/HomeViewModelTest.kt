package com.cotesa.murcia.feature.home.viewmodel

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.cotesa.appcore.functional.Either
import com.cotesa.common.entity.beach.Beach
import com.cotesa.murcia.feature.home.usecase.GetBeachesDB
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class HomeViewModelTest{

    @RelaxedMockK
    private lateinit var getBeachesBD: GetBeachesDB

    @RelaxedMockK
    private lateinit var context : Context

    private lateinit var homeViewModel: HomeViewModel

    @get:Rule
    var rule: InstantTaskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun onBefore(){
        MockKAnnotations.init(this)
        homeViewModel = HomeViewModel(getBeachesBD)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }
    @After
    fun onAfter(){
        Dispatchers.resetMain()
    }

    @Test
    fun `when we call beaches and receive list of beaches, set beachList`() = runTest{
        //Given
        val beachList = listOf(
        Beach().apply {
            nid = 0
            title = "Playa 1"
        },
            Beach().apply {
                nid = 1
                title = "Playa 2"
            },
            Beach().apply {
                nid = 2
                title = "Playa 3"
            },)
        coEvery { getBeachesBD.run(GetBeachesDB.Params(context)) } returns Either.Right(beachList)

        //When
        homeViewModel.callBeaches(context)

        //Then
        assert(homeViewModel.beachList.value == beachList)
    }
}