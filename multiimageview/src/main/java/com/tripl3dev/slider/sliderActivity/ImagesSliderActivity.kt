package com.tripl3dev.slider.sliderActivity

import android.animation.Animator
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageButton
import com.tripl3dev.multiimageview.MultibleImageConfig
import com.tripl3dev.multiimageview.R
import com.tripl3dev.slider.sliderAnimation.AlphaPageTransformer
import com.tripl3dev.slider.sliderAnimation.BasePageTransformer
import com.tripl3dev.slider.sliderAnimation.CubePageTransformer
import com.tripl3dev.slider.sliderAnimation.RotatePageTransformer
import com.tripl3dev.slider.sliderBottomList.ImagesListAdapter
import com.tripl3dev.slider.sliderBottomList.ImagesListAdapter.SliderViewHandlerListener
import com.tripl3dev.slider.sliderContent.MySliderAdapter

class ImagesSliderActivity : AppCompatActivity() {

    lateinit var imagesListView: RecyclerView
    lateinit var imagesPager: ViewPager
    lateinit var backButton: ImageButton
    lateinit var mAdapter: ImagesListAdapter
    lateinit var sliderConfig: MultibleImageConfig

    companion object {
        const val IMAGES_SLIDER_CONFIG = "IMAGES_SLIDER_CONFIG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sliderConfig = intent.getSerializableExtra(IMAGES_SLIDER_CONFIG) as MultibleImageConfig
        setContentView(sliderConfig.slideActivityView)
        imagesListView = findViewById(R.id.multibleImageList)
        imagesPager = findViewById(R.id.multibleImageSlider)
        backButton = findViewById(R.id.closeButton)

        val adapter = MySliderAdapter(supportFragmentManager, sliderConfig)
        mAdapter = ImagesListAdapter(sliderConfig, object : SliderViewHandlerListener {
            override fun onItemSelected(position: Int) {
                imagesPager.currentItem = position
                imagesListView.scrollToPosition(position)
            }
        })
        imagesPager.setPageTransformer(false, sliderConfig.viewPagerAnimation.getAnimationType())
        imagesPager.adapter = adapter
        handleOrientation()
        imagesListView.layoutManager = LinearLayoutManager(this, sliderConfig.imagesListOrientation, false)
        imagesListView.setHasFixedSize(true)
        imagesListView.adapter = mAdapter
        imagesPager.currentItem = sliderConfig.imageNum
        mAdapter.selectItem(sliderConfig.imageNum)
        imagesListView.scrollToPosition(sliderConfig.imageNum)
        backButton.setOnClickListener {
            finish()
        }


        imagesPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageSelected(position: Int) {
                imagesListView.scrollToPosition(position)
                mAdapter.selectItem(position)
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

            }

        })
    }

    private fun handleOrientation() {

        if (sliderConfig.isJustPortrait){
            requestedOrientation=ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        }
        if (sliderConfig.slideActivityView==R.layout.activity_images_slider){
            if (resources.configuration.orientation== Configuration.ORIENTATION_LANDSCAPE)
                sliderConfig.imagesListOrientation=1
            else
                sliderConfig.imagesListOrientation=0
        }

    }


    fun Int.getAnimationType(): BasePageTransformer {
        return when (this) {
            0 -> {
                AlphaPageTransformer()
            }
            1 -> {
                CubePageTransformer()
            }
            2 -> {
                RotatePageTransformer()
            }
            else -> {
                AlphaPageTransformer()
            }
        }

    }

    fun toggleImagesListVisibilty() {
        val viewProperty = imagesListView.animate()
        if (imagesListView.visibility == View.VISIBLE) {
            if (sliderConfig.imagesListOrientation == RecyclerView.HORIZONTAL)
                viewProperty.scaleY(0.0f)
            else
                viewProperty.scaleX(0.0f)

            viewProperty.setDuration(150).setListener(object : Animator.AnimatorListener {
                override fun onAnimationRepeat(animation: Animator?) {
                }

                override fun onAnimationEnd(animation: Animator?) {
                    imagesListView.visibility = View.GONE
                }

                override fun onAnimationCancel(animation: Animator?) {

                }

                override fun onAnimationStart(animation: Animator?) {
                }

            }).start()

        } else {
            imagesListView.visibility = View.VISIBLE
            if (sliderConfig.imagesListOrientation == RecyclerView.HORIZONTAL)
                viewProperty.scaleY(1.0f)
            else
                viewProperty.scaleX(1.0f)

            viewProperty.setDuration(150).setListener(null).start()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        for (item in sliderConfig.imagesList) {
            item.setSelected(false
            )
        }
    }
}
