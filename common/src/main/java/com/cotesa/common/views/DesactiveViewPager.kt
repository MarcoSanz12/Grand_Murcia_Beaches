package com.cotesa.common.views

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent


import androidx.viewpager.widget.ViewPager


class DesactiveViewPager : ViewPager {
    private var isPagingEnabled = false

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onTouchEvent(event)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return isPagingEnabled && super.onInterceptTouchEvent(event)
    }

    override fun setCurrentItem(item: Int) {
        super.setCurrentItem(item, false )
    }

}