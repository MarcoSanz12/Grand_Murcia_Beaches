package com.cotesa.common.callbackl

interface TypedCallback<T> {
    fun onCallback(response: T)
}