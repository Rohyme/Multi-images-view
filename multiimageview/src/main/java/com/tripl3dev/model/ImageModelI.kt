package com.tripl3dev.model

import java.io.Serializable

/**
 * Created by Rohyme on 4/11/2018.
 */
 interface ImageModelI:Serializable{
    fun getImageUrl(): String
    fun getImageDetails(): String? {
        return ""
    }
    fun isSelected():Boolean?{
      return false
    }
    fun setSelected(isSelected : Boolean)
}