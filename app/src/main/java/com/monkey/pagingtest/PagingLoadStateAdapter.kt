package com.monkey.pagingtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView

class PagingLoadStateAdapter(
    private val retry : () -> Unit
) : LoadStateAdapter<PagingLoadStateHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PagingLoadStateHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val holder = PagingLoadStateHolder(layoutInflater.inflate(R.layout.item_loadstate, null))
        holder.itemView.layoutParams = RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT)
        holder.button.setOnClickListener {
            Toast.makeText(holder.itemView.context, "retry", Toast.LENGTH_SHORT).show()
            retry()
        }
        return holder
    }

    override fun onBindViewHolder(holder: PagingLoadStateHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

}

class PagingLoadStateHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvMessage = itemView.findViewById<TextView>(R.id.tv_loadstate)
    val progress = itemView.findViewById<ProgressBar>(R.id.pb_loadstate)
    val button = itemView.findViewById<Button>(R.id.btn_loadstate)

    fun bind(state: LoadState) {
        tvMessage.text = when(state) {
            is LoadState.Loading -> "Loading..."
            is LoadState.NotLoading -> "Not Loading!!"
            is LoadState.Error -> "Error!!"
            else -> "ELSE"
        }
        progress.visibility = if(state is LoadState.Loading) View.VISIBLE else View.INVISIBLE
        button.visibility = if(state is LoadState.Error) View.VISIBLE else View.INVISIBLE
    }

}