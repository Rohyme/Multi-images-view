package com.tripl3dev.slider.sliderContent

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tripl3dev.model.ImageModelI
import com.tripl3dev.multiimageview.MultibleImageConfig
import com.tripl3dev.multiimageview.R
import com.tripl3dev.slider.sliderActivity.ImagesSliderActivity
import java.lang.Exception

class ImageFragment : Fragment() {
    var imageUrl = ""
    var imageDescription = ""
    lateinit var config: MultibleImageConfig

    companion object {


        fun createImageFragment(imageModel: ImageModelI, config: MultibleImageConfig ): ImageFragment {
            val imageFragment = ImageFragment()
            val args = Bundle()
            args.putString("Image_Url", imageModel.getImageUrl())
            args.putString("Image_Description", imageModel.getImageDetails())
            args.putSerializable("Config", config)
            imageFragment.arguments = args
            return imageFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageUrl = arguments!!.getString("Image_Url")
        imageDescription = arguments!!.getString("Image_Description","")
        config = arguments!!.getSerializable("Config") as MultibleImageConfig
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(config.slideImgWithDescView , container, false)
        val imageDescriptionView = view.findViewById<TextView>(R.id.imageDescView)
        val imageSlideView = view.findViewById<ImageView>(R.id.imageSlideView)
        val imageProgressView = view.findViewById<ProgressBar>(R.id.imageProgress)
        if (imageDescription.isNotEmpty()){
            imageDescriptionView.text =  imageDescription
        }else{
            imageDescriptionView.visibility =View.GONE
        }
        Picasso.get().load(imageUrl)
                .placeholder(config.imgPlaceHolder)
                .error(config.imgErrorHolder)
                .into(imageSlideView, object : Callback {
            override fun onSuccess() {
                if (imageProgressView!=null)
                imageProgressView.visibility =  View.GONE
            }

            override fun onError(e: Exception?) {
                if (imageProgressView!=null)
                imageProgressView.visibility =  View.GONE
            }

        })
        imageSlideView.setOnClickListener {
            (context as ImagesSliderActivity).toggleImagesListVisibilty()
        }
        return view
    }


}