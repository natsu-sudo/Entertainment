package com.coding.entertainmentlist.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.coding.entertainmentlist.R
import com.coding.entertainmentlist.database.TvSeries
import com.coding.entertainmentlist.network.TmdbService

class ListAdapterForView(private val listener: (Long) -> Unit) : ListAdapter<TvSeries,ListAdapterForView.ViewHolder>(DiffCallback()){
    inner class ViewHolder(private val view: View):RecyclerView.ViewHolder(view) {
        private val poster: ImageView =view.findViewById(R.id.movie_poster)
        private val tvTitle: TextView =view.findViewById(R.id.move_title)
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition).id)
            }
        }

        fun onBind(item: TvSeries) {
            tvTitle.text=item.title
            Glide.with(view)
                .load(TmdbService.BACKDROP_BASE_URL+item.posterPath)
                .error(R.drawable.ic_launcher_background)
                .into(poster)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view=LayoutInflater.from(parent.context).inflate( R.layout.lists,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }


}

class DiffCallback: DiffUtil.ItemCallback<TvSeries>() {
    override fun areItemsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem.id==newItem.id
    }

    override fun areContentsTheSame(oldItem: TvSeries, newItem: TvSeries): Boolean {
        return oldItem==newItem
    }

}
