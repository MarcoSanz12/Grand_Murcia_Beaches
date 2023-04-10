package com.cotesa.common.util

import com.cotesa.common.entity.common.BeachActionBar

class HomeActionBar(title: String) :
    BeachActionBar(
        title = title,
        haveBack = false,
        haveOrder = null,
        orderFunction = null,
        haveFilter = false,
        filterFunction = null,
        haveSearch = false,
        searchFunction = null,
        haveFavorite = false,
        favoriteFunction = null,
        closeFunction = null
    )

class ListActionBar(title:String, orderFunction : (order:OrderState)->Unit, searchFunction: (() -> Unit)) :
        BeachActionBar (
            title = title,
            haveBack = false,
            haveOrder = OrderState.ALPHABETICAL,
            orderFunction = orderFunction,
            haveFilter = false,
            filterFunction = null,
            haveSearch = true,
            searchFunction = searchFunction,
            haveFavorite = false,
            favoriteFunction = null,
            closeFunction = null
                )


class DefaultActionBar(title: String) : BeachActionBar(
    title = title,
    haveBack = false,
    haveOrder = null,
    orderFunction = null,
    haveFilter = false,
    filterFunction = null,
    haveSearch = false,
    searchFunction = null,
    haveFavorite = false,
    favoriteFunction = null,
    closeFunction = null
)


