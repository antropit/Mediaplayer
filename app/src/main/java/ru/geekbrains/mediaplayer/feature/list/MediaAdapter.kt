package ru.geekbrains.mediaplayer.feature.list

import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.view_list_item.view.*
import ru.geekbrains.mediaplayer.R
import ru.geekbrains.mediaplayer.model.MediaSourceEntity

class MediaAdapter(private val listener: (MediaSourceEntity) -> Unit):
    ListAdapter<MediaSourceEntity, MediaAdapter.MediaHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<MediaSourceEntity>() {
            override fun areItemsTheSame(p0: MediaSourceEntity, p1: MediaSourceEntity): Boolean {
                return p0.id == p1.id
            }

            override fun areContentsTheSame(p0: MediaSourceEntity, p1: MediaSourceEntity): Boolean {
                return p0 == p1
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): MediaHolder {
        return MediaHolder(LayoutInflater.from(parent.context).inflate(R.layout.view_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: MediaHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class MediaHolder(private val view: View): RecyclerView.ViewHolder(view) {
        fun bind(item: MediaSourceEntity) {
            view.name.text = item.name
            Glide.with(view.context)
                .load(item.imagePath)
                //.placeholder() //TODO add placeholder
                .into(view.logo)

            if (item.isPlaying) view.vuMeter.visibility = View.VISIBLE else view.vuMeter.visibility = View.GONE

            view.setOnClickListener { listener.invoke(item) }
        }
    }
}