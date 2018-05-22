package com.tripl3dev.slider.sliderAnimation

import android.support.v4.view.ViewCompat.setAlpha
import android.support.v4.view.ViewCompat
import android.view.View


class AlphaPageTransformer :BasePageTransformer(){
    private var mMinScale = 0.5f


    fun AlphaPageTransformer(minScale: Float) {
        setMinScale(minScale)
    }

    override fun handleInvisiblePage(view: View, position: Float) {
        ViewCompat.setAlpha(view, 0f)
    }

    override fun handleLeftPage(view: View, position: Float) {
        view.alpha = mMinScale + (1 - mMinScale) * (1 + position)
    }

    override fun handleRightPage(view: View, position: Float) {
        view.alpha = mMinScale + (1 - mMinScale) * (1 - position)
    }

    fun setMinScale(minScale: Float) {
        mMinScale = minScale
    }

}