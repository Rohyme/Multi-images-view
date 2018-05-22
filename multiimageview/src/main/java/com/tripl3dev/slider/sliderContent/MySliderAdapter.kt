package com.tripl3dev.slider.sliderContent

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.tripl3dev.multiimageview.MultibleImageConfig

class MySliderAdapter(fragmentManager: FragmentManager, val config :MultibleImageConfig) : FragmentStatePagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return ImageFragment.createImageFragment(config.imagesList[position],config)
    }

    override fun getCount(): Int {
        return config.imagesList.size
    }
}