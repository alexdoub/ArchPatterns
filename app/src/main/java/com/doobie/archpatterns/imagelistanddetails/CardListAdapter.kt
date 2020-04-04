package com.doobie.archpatterns.imagelistanddetails

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.doobie.archpatterns.databinding.CardListItemBinding
import com.doobie.archpatterns.imagelistanddetails.model.CardListItem
import com.squareup.picasso.Picasso

/**
 * Created by Alex Doub on 3/28/2020.
 */

internal class CardListAdapter(private val listener: IOnCardClickedListener) : RecyclerView.Adapter<CardListItemViewHolder>() {

    interface IOnCardClickedListener {
        fun onCardClicked(id: String)
    }

    var items = emptyList<CardListItem>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardListItemViewHolder {
        return CardListItemViewHolder(parent, listener)
    }

    override fun onBindViewHolder(holder: CardListItemViewHolder, position: Int) {
        holder.bind(items[position])
    }

}

internal class CardListItemViewHolder(
    parent: ViewGroup,
    private val listener: CardListAdapter.IOnCardClickedListener,
    private val binding: CardListItemBinding = CardListItemBinding.inflate(
        LayoutInflater.from(
            parent.context
        ), parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: CardListItem) {
        Picasso.get().load(item.imgUrl).into(binding.image)
        binding.title.text = item.name

        binding.root.setOnClickListener { listener.onCardClicked(item.id) }
    }
}