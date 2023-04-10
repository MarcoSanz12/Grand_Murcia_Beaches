package com.cotesa.common.util

import android.content.Context
import com.cotesa.common.entity.beach.Beach

class SettingsUtils {

    companion object Factory {

        fun isInFavorites(context: Context, beach: Beach): Boolean {

            val prefs = context.getSharedPreferences(Constant.USER_SETTINGS, 0)

            val favBeachesSet =
                prefs.getStringSet(Constant.USER_SETTINGS_FAV_BEACHES, null) ?: mutableSetOf()

            return favBeachesSet.contains(beach.nid.toString())
        }

        fun orderBeachListByFavorites(context: Context, beachList: List<Beach>): List<Beach> {
            val favorites = mutableListOf<Beach>()
            val nonFavorites = mutableListOf<Beach>()

            for (b in beachList) {
                if (isInFavorites(context, b))
                    favorites.add(b)
                else
                    nonFavorites.add(b)
            }
            return mutableListOf<Beach>().apply {
                addAll(favorites.sortedBy { it.title })
                addAll(nonFavorites.sortedBy { it.title })
            }.toList()


        }
    }
}