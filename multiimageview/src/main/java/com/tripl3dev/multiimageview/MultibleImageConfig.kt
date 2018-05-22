package com.tripl3dev.multiimageview

import com.tripl3dev.model.ImageModelI
import java.io.Serializable

class MultibleImageConfig internal  constructor(): Serializable {

    internal var imagesList: ArrayList<out ImageModelI> = ArrayList()
    internal var imageNum = 0
    internal var imagesListOrientation: Int = 0


    var slideImgWithDescView = R.layout.image_view
    var slideActivityView = R.layout.activity_images_slider
    var imgsListSelectedView: Int = R.layout.images_list_item_selected
    var imgsListUnSelectedView: Int = R.layout.images_list_item_unselected
    var viewPagerAnimation: Int = 0
    var imgErrorHolder: Int=R.drawable.img_error
    var imgPlaceHolder: Int=R.drawable.img_placeholder
    var isJustPortrait: Boolean =false

}