package com.cotesa.murcia.util

import com.cotesa.common.entity.beach.Beach
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BeachProvider @Inject constructor() {
    var beachList = listOf<Beach>()
}