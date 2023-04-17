package com.cotesa.common.util

import android.content.Context
import com.cotesa.common.entity.beach.Beach
import javax.inject.Inject

class BeachComparator(val context: Context) : Comparator<Beach> {

    override fun compare(b0: Beach?, b1: Beach?): Int {

        val preferences =  context.getSharedPreferences(Constant.USER_SETTINGS,0)
        val favoritesList = preferences.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES,null)

        //TODO: Acabar comparator
        val fav0 = SettingsUtils.isInFavorites(context,b0!!)
        val fav1 = SettingsUtils.isInFavorites(context,b1!!)

        // If both favorites / or not, return compareBy alphabetically
        return if (fav0 == fav1)
            b0.compareTo(b1)
        else
            //Else return which one is favorite
            if (fav0)
                -1
            else
                1
    }
}