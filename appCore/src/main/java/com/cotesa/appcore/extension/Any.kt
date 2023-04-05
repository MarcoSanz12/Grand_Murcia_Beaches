package com.cotesa.appcore.extension

import android.content.Context
import android.content.res.Resources
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Build


fun <T : Any> T?.notNull(f: (it: T) -> Unit) {
    if (this != null) f(this)
}

