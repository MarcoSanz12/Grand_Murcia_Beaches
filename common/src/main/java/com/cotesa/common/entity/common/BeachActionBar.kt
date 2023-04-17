package com.cotesa.common.entity.common

import com.cotesa.appcore.platform.ConfigureActionBar
import com.cotesa.common.util.OrderState

open class BeachActionBar(
    var title: String,
    var haveBack: Boolean,
    var haveOrder: OrderState?,
    var orderFunction: ((order: OrderState) -> Unit)?,
    var haveFilter: Boolean,
    var filterFunction: (() -> Unit)?,
    var haveSearch: Boolean,
    var searchFunction: ((text: String) -> Unit)?,
    var closeFunction: (() -> Unit)?,
    var haveFavorite: Boolean,
    var favoriteFunction: (() -> Unit)?
) : ConfigureActionBar