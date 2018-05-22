package com.tripl3dev.multibleimagelib

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tripl3dev.model.ImageModel
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        val imagesList: ArrayList<ImageModel> = ArrayList()
        resources.getStringArray(R.array.imgss).asList().forEachIndexed { index, imageUrl ->
            imagesList.add(ImageModel(imageUrl, "Image No $index"))
        }
        imagesView.intialize()
        imagesView.setImages(imagesList)

    }
}
