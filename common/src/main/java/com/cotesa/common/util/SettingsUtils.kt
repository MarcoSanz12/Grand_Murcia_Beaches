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

    }
}