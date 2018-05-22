package com.tripl3dev.slider.sliderAnimation


import android.view.View

class CubePageTransformer : BasePageTransformer() {
    private var mMaxRotation = 90.0f


    fun CubePageTransformer(maxRotation: Float) {
        setMaxRotation(maxRotation)
    }

    override fun handleInvisiblePage(view: View, position: Float) {}

    override fun handleLeftPage(view: View, position: Float) {

        view.pivotX = view.measuredWidth.toFloat()
        view.pivotY = view.measuredHeight * 0.5f
        view.rotationY = mMaxRotation * position
    }

    override fun handleRightPage(view: View, position: Float) {
        //设置旋转中心点
        view.pivotX = 0.0f
        view.pivotY = view.measuredHeight * 0.5f
        view.rotationY = mMaxRotation * position
    }

    fun setMaxRotation(maxRotation: Float) {
        if (maxRotation in 0.0f..90.0f) {
            mMaxRotation = maxRotation
        }
    }
}
