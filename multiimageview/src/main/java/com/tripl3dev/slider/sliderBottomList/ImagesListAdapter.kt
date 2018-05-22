package com.tripl3dev.slider.sliderBottomList

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.tripl3dev.model.ImageModelI
import com.tripl3dev.multiimageview.MultibleImageConfig
import com.tripl3dev.multiimageview.R
import java.lang.Exception


class ImagesListAdapter(var config: MultibleImageConfig, var sliderViewListener: SliderViewHandlerListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var previousSelectedPosition: Int = -1
    val ITEM_SELECTED_TYPE = 30
    val ITEM_UNSELECTED_TYPE = 33
    var oldList: ArrayList<out ImageModelI> = ArrayList(config.imagesList)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SELECTED_TYPE) ImagesListVH(LayoutInflater.from(parent.context).inflate(config.imgsListSelectedView, parent, false))
        else ImagesListVH(LayoutInflater.from(parent.context).inflate(config.imgsListUnSelectedView, parent, false))
    }

    override fun getItemCount(): Int {
        return config.imagesList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (config.imagesList[position].isSelected()!!) ITEM_SELECTED_TYPE else ITEM_UNSELECTED_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentItem = config.imagesList[position]
        Picasso.get().load(currentItem.getImageUrl())
                .placeholder(config.imgPlaceHolder)
                .error(config.imgErrorHolder)
                .into((holder as ImagesListVH).image, object : Callback {
                    override fun onSuccess() {
                        if (holder.progress != null)
                            holder.progress!!.visibility = View.GONE
                    }

                    override fun onError(e: Exception?) {
                        if (holder.progress != null)
                            holder.progress!!.visibility = View.GONE
                    }

                })
        holder.itemView.setOnClickListener {
            selectItem(position)
            sliderViewListener.onItemSelected(position)
        }
    }


    fun selectItem(position: Int) {
        if (previousSelectedPosition != -1) {
            config.imagesList[previousSelectedPosition].setSelected(false)
        }
        config.imagesList[position].setSelected(true)
        previousSelectedPosition = position
        notifyChanges()
    }

    fun notifyChanges() {
        val diffResult = DiffUtil.calculateDiff(ImagesListDiffCallback(oldList, config.imagesList))
        diffResult.dispatchUpdatesTo(this)
        oldList = config.imagesList
    }


    class ImagesListVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView? = null
        var progress: ProgressBar? = null

        init {
            image = itemView.findViewById(R.id.slideViewItemImage)
            progress = itemView.findViewById(R.id.imageProgress)
        }
    }

    interface SliderViewHandlerListener {
        fun onItemSelected(position: Int)
    }
}
