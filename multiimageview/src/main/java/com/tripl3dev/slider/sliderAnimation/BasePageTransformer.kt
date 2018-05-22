package com.tripl3dev.slider.sliderAnimation

import android.support.v4.view.ViewPager
import android.view.View


abstract class BasePageTransformer : ViewPager.PageTransformer {
    /**
     * Apply a property transformation to the given page.
     *
     * @param view     Apply the transformation to this page
     * @param position Position of page relative to the current front-and-center
     * position of the pager. 0 is front and center. 1 is one full
     */
    override fun transformPage(view: View, position: Float) {
        when {
            position < -1.0f -> // [-Infinity,-1)
                // This page is way off-screen to the left.
                handleInvisiblePage(view, position)
            position <= 0.0f -> // [-1,0]
                // Use the default slide transition when moving to the left page
                handleLeftPage(view, position)
            position <= 1.0f -> // (0,1]
                handleRightPage(view, position)
            else -> // (1,+Infinity]
                // This page is way off-screen to the right.
                handleInvisiblePage(view, position)
        }
    }

    abstract fun handleInvisiblePage(view: View, position: Float)

    abstract fun handleLeftPage(view: View, position: Float)

    abstract fun handleRightPage(view: View, position: Float)
}