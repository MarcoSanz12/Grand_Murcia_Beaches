package com.cotesa.common.util

import android.content.Context
import androidx.core.content.edit
import androidx.test.core.app.ApplicationProvider
import com.cotesa.common.entity.beach.Beach
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class SettingsUtilsTest{

    val context = ApplicationProvider.getApplicationContext<Context>()

    @Before
    fun setBefore (){
        loadSharedList()
    }
    @After
    fun setAfter(){

    }

    // isInFavorites
    @Test
    fun if_beach_is_in_favorites_return_TRUE(){

        val beach = Beach().apply { nid = 1 }
        assertTrue(SettingsUtils.isInFavorites(context,beach))
    }

    @Test
    fun if_beach_is_NOT_in_favorites_return_FALSE(){

        val beach = Beach().apply { nid = 999 }
        assertFalse(SettingsUtils.isInFavorites(context,beach))
    }

    @Test
    fun when_beach1isNonFavorite_and_beach2isFavorite_RETURN_true_if_beach2_is_first(){
        val beachList = listOf<Beach>(
            Beach().apply { nid=999
                            title = "beach1"},
            Beach().apply { nid=1
                            title = "beach2"}
        )

        assertTrue(SettingsUtils.orderBeachListByFavorites(context,beachList).first() == beachList[1])
    }

    @Test
    fun when_beach1isFavorite_and_beach2isFavorite_RETURN_true_if_beach1_is_first(){
        val beachList = listOf<Beach>(
            Beach().apply { nid=2
                title = "beach1"},
            Beach().apply { nid=1
                title = "beach2"}
        )

        assertTrue(SettingsUtils.orderBeachListByFavorites(context,beachList).first() == beachList[0])
    }

    @Test
    fun whenListIsOrderedByFavorites_equalsManuallyOrderedList_RETURN_true_isEquallyOrdered(){
        val beachList = listOf<Beach>(
            //Favorites so first
            Beach().apply { nid=1
                title = "beach1"},
            Beach().apply { nid=2
                title = "beach2"},
            Beach().apply { nid=3
                    title = "beach3"},
            //NonFavorites so later
            Beach().apply { nid=4
                title = "beach4"},
            Beach().apply { nid=5
                title = "beach5"}
        )
        // Shuffle and compare
        assertTrue(SettingsUtils.orderBeachListByFavorites(context,beachList.shuffled()) == beachList)
    }

    private fun loadSharedList(){
        val prefs = context.getSharedPreferences(Constant.USER_SETTINGS, 0)

        prefs.edit().clear().apply()
        prefs.edit().putStringSet(Constant.USER_SETTINGS_FAV_BEACHES, setOf("1","2","3")).apply()

    }

    private fun clearSharedList(){
        val prefs = context.getSharedPreferences(Constant.USER_SETTINGS, 0)

        prefs.edit().clear().apply()
    }
}