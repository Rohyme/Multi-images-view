package com.tripl3dev.slider.sliderBottomList

import android.support.v7.util.DiffUtil
import com.tripl3dev.model.ImageModelI

 class ImagesListDiffCallback(val oldList: List<out ImageModelI>, val newList: List<out ImageModelI>) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].isSelected() == newList[newItemPosition].isSelected() &&
                oldList[oldItemPosition].getImageDetails() == newList[newItemPosition].getImageDetails() &&
                oldList[oldItemPosition].getImageUrl() == newList[newItemPosition].getImageUrl()
    }

    override fun getOldListSize(): Int {
    return oldList.size

    }

    override fun getNewListSize(): Int {
        return  newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return  oldList[oldItemPosition] == newList[newItemPosition]
    }

}