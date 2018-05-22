package com.tripl3dev.multiimageview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.ImageView.ScaleType
import android.widget.LinearLayout
import android.widget.TextView
import com.squareup.picasso.Picasso
import com.tripl3dev.model.ImageModel
import com.tripl3dev.model.ImageModelI
import com.tripl3dev.slider.sliderActivity.ImagesSliderActivity
import de.hdodenhof.circleimageview.CircleImageView
import java.lang.Exception

/**
 * Created by Rohyme on 4/11/2018.
 */

class MultibleImageView : LinearLayout {

    val NORMAL_IMAGE = 0
    var isNormalImage = true
    var imagesList: ArrayList<out ImageModelI> = ArrayList()
    var spaceBetweenImages = 0
    var textSize = 0
    var textColor = Color.WHITE
    var imagePlaceHolder: Int = R.drawable.img_placeholder
    var imageErrorDrawable: Int = R.drawable.img_error
    var circleImageBorderColor = 0
    var circeImageBorderWidth = 1
    var haveMorePics = true
    private var scaleType: Int = 1
    private val sScaleTypeArray = arrayOf(ScaleType.MATRIX, ScaleType.FIT_XY, ScaleType.FIT_START, ScaleType.FIT_CENTER, ScaleType.FIT_END, ScaleType.CENTER, ScaleType.CENTER_CROP, ScaleType.CENTER_INSIDE)


    private lateinit var imageClickListener: onImageClickedListener

    private var eachViewWidth: Int = 0
    private val maxImagesPerScreen: Int
        get() {
            return if (maxImagesNum != 0) {
                maxImagesNum
            } else {
                val metrics = DisplayMetrics()
                (this.context as Activity).windowManager.defaultDisplay.getMetrics(metrics)
                eachViewWidth = if (eachImageWidth != 0f) eachImageWidth.toInt() else {
                    180
                }
                val screenWidth = width
                screenWidth / eachViewWidth
            }
        }


    constructor(context: Context) : super(context) {
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setAttributes(attrs!!)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle) {
        setAttributes(attrs!!)
    }


    private fun createListImages(imgUrl: String, isTheLast: Boolean, imageNum: Int) {
        val img = getImage()
        val imgParams: LayoutParams = if (imagesList.size > maxImagesPerScreen) {
            LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        } else {
            LinearLayout.LayoutParams(eachViewWidth, ViewGroup.LayoutParams.MATCH_PARENT)
        }
        if (!isTheLast) {
            imgParams.marginEnd = spaceBetweenImages
        }
        img.layoutParams = imgParams

        img.setOnClickListener({ imageClickListener.onImageClicked(imageNum) })
        Log.e("ImageNum", " : $imageNum")

        if (URLUtil.isValidUrl(imgUrl)) {
            imgUrl.setToImage(img)
        }
        addView(img)
    }

    private fun init() {
        val imagesPerScreen = maxImagesPerScreen
        var isTheLast = false
        if (maxImagesPerScreen < imagesList.size) {
            haveMorePics = true
        }
        for (i in 1..imagesPerScreen) {
            if (i == imagesList.size) {
                isTheLast = true
            }
            if (imagesList.size >= i) {
                if (i != imagesPerScreen) {
                    createListImages(imagesList[i - 1].getImageUrl(), isTheLast, i - 1)
                }
                //last image
                if (i == imagesPerScreen) {
                    if (imagesList.size - (imagesPerScreen - 1) > 0) {
                        createFinalImage(imagesList[i - 1].getImageUrl(), imagesList.size - (imagesPerScreen - 1), i - 1)
                    } else {
                        createListImages(imagesList[i - 1].getImageUrl(), isTheLast, i - 1)
                    }
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun createFinalImage(imageUrl: String, maxImagesPerScreen: Int, imageNum: Int) {
        val finalImageContainer = FrameLayout(context)
        val img = getImage()
        val imagesCounterTextView = TextView(context)
        val containerParams = LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT, 1f)
        val imgParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        val counterParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        finalImageContainer.layoutParams = containerParams
        img.layoutParams = imgParams
        img.setColorFilter(finalImage, PorterDuff.Mode.OVERLAY)
        imagesCounterTextView.layoutParams = counterParams
        finalImageContainer.addView(img)
        finalImageContainer.addView(imagesCounterTextView)
        imagesCounterTextView.setTextColor(textColor)
        imagesCounterTextView.textSize = textSize.toFloat()
        imagesCounterTextView.text = "+ $maxImagesPerScreen"
        imagesCounterTextView.gravity = Gravity.CENTER
        finalImageContainer.setOnClickListener({ imageClickListener.onImageClicked(imageNum) })
        Log.e("ImageNum", " : $imageNum")
        imageUrl.setToImage(img)
        addView(finalImageContainer)
    }


    private fun String.setToImage(img: ImageView) {
        val requestCreator = Picasso.get().load(this)
        requestCreator.placeholder(imagePlaceHolder)
        requestCreator.error(imageErrorDrawable)
        requestCreator.into(img)
    }

    fun getImage(): ImageView {
        return if (isNormalImage) {
            val imgg = ImageView(context)
            for (i in 0..6) {
            }
            imgg.scaleType = sScaleTypeArray[scaleType]
            imgg
        } else {
            val circleImageView = CircleImageView(context)
            circleImageView.borderColor = circleImageBorderColor
            circleImageView.borderWidth = circeImageBorderWidth
            circleImageView
        }
    }


    private var finalImage: Int = 0

    private var customSliderView: Int = 0

    private var customSliderActivityView: Int = -10

    private var imagesListOrientation: Int = 0

    private var eachImageWidth: Float = 0.0f

    private var maxImagesNum: Int = 0

    private var customSliderImgListSelectedV: Int = 0

    private var customSliderImgListUnSelectedV: Int = 0

    private var sliderTransitionAnimation: Int = 0

    private var isJustPortrait: Boolean =false

    private fun setAttributes(attributes: AttributeSet) {
        val attrs = context.theme.obtainStyledAttributes(attributes, R.styleable.multibleImageView, 0, 0)
        isNormalImage = attrs.getInt(R.styleable.multibleImageView_mi_imagesType, 0) == NORMAL_IMAGE
        finalImage = attrs.getColor(R.styleable.multibleImageView_mi_finalImageOverlayColor, Color.argb(120, 201, 8, 37))
        textColor = attrs.getColor(R.styleable.multibleImageView_mi_textColor, Color.WHITE)
        textSize = attrs.getDimension(R.styleable.multibleImageView_mi_finalImageTextSize, 14f).toInt()
        imagePlaceHolder = attrs.getResourceId(R.styleable.multibleImageView_mi_imagePlaceHolder, R.drawable.img_placeholder)
        imageErrorDrawable = attrs.getResourceId(R.styleable.multibleImageView_mi_imageErrorDrawable, R.drawable.img_error)
        circleImageBorderColor = attrs.getColor(R.styleable.multibleImageView_mi_circleImageBorderColor, Color.WHITE)
        circeImageBorderWidth = attrs.getDimension(R.styleable.multibleImageView_mi_circleImageBorderWidth, 2f).toInt()
        spaceBetweenImages = attrs.getDimension(R.styleable.multibleImageView_mi_spaceBetweenImages, 0f).toInt()
        scaleType = attrs.getInt(R.styleable.multibleImageView_mi_scaleType, 1)
        customSliderView = attrs.getResourceId(R.styleable.multibleImageView_mi_customSlideViewWithDesc,R.layout.image_view)
        customSliderActivityView = attrs.getResourceId(R.styleable.multibleImageView_mi_customSliderActivityView, R.layout.activity_images_slider)
        imagesListOrientation = attrs.getInt(R.styleable.multibleImageView_mi_imagesListOrientation, 0)
        eachImageWidth = attrs.getDimension(R.styleable.multibleImageView_mi_imageWidth, 180f)
        maxImagesNum = attrs.getInt(R.styleable.multibleImageView_mi_maxImagesNum, 0)
        customSliderImgListSelectedV = attrs.getResourceId(R.styleable.multibleImageView_mi_sliderImagesListSelectedView, R.layout.images_list_item_selected)
        customSliderImgListUnSelectedV = attrs.getResourceId(R.styleable.multibleImageView_mi_sliderImagesListUnSelectedView, R.layout.images_list_item_unselected)
        sliderTransitionAnimation = attrs.getInt(R.styleable.multibleImageView_mi_sliderTransitionAnimation, 0)
        isJustPortrait = attrs.getBoolean(R.styleable.multibleImageView_mi_sliderOrientationJustPortrait, false)
    }

    private fun start() {
        this.post {
            width
            this.init()
        }
    }


    fun setImages(imagesUrls: ArrayList<out ImageModelI>) {
        if (!this::config.isInitialized) {
            throw Exception("You have to intialize MultiImageView before setImages to it ..!")
        }

        imagesList = imagesUrls
        config.imagesList = imagesList
        imageClickListener = object : onImageClickedListener {
            override fun onImageClicked(imageNum: Int) {
                config.imageNum = imageNum
                val intent = Intent(context, ImagesSliderActivity::class.java)
                intent.putExtra(ImagesSliderActivity.IMAGES_SLIDER_CONFIG, config)
                context.startActivity(intent)
            }
        }
        start()
    }

    fun intialize() {
        setConfig()
    }

    fun setImages(imagesUrls: List<String>) {
        if (!this::config.isInitialized) {
            throw Exception("You have to intialize MultiImageView before setImages to it ..!")
        }
        imagesList = ArrayList(imagesUrls.map { ImageModel(it) })
        config.imagesList = imagesList
        imageClickListener = object : onImageClickedListener {
            override fun onImageClicked(imageNum: Int) {
                val intent = Intent(context, ImagesSliderActivity::class.java)
                config.imageNum = imageNum
                intent.putExtra(ImagesSliderActivity.IMAGES_SLIDER_CONFIG, config)
                context.startActivity(intent)
            }
        }
        start()
    }

    private lateinit var config: MultibleImageConfig


    private fun setConfig() {
        config = MultibleImageConfig()
        config.imagesListOrientation = imagesListOrientation
        config.slideActivityView = customSliderActivityView
        config.slideImgWithDescView = customSliderView
        config.imgsListSelectedView = customSliderImgListSelectedV
        config.imgsListUnSelectedView = customSliderImgListUnSelectedV
        config.viewPagerAnimation = sliderTransitionAnimation
        config.imgErrorHolder = imageErrorDrawable
        config.imgPlaceHolder = imagePlaceHolder
        config.isJustPortrait = isJustPortrait
    }

    fun getMultibleImgsConfig(): MultibleImageConfig {
        return config
    }

    fun setMultibleImgsConfig(configObject: MultibleImageConfig) {
        config = configObject
    }

    fun calculateNoOfColumns(context: Context): Int {
        val displayMetrics = context.resources.displayMetrics
        val dpWidth = displayMetrics.widthPixels / displayMetrics.density
        return (dpWidth / 180).toInt()
    }


    interface onImageClickedListener {
        fun onImageClicked(imageNum: Int)
    }


}
