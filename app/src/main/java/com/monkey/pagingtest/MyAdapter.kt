package com.monkey.pagingtest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

/**
 * RecyclerView.Adapter에서 PagingDataAdapter로 변경 시
 * 내부적으로 리스트가 만들어져서 관리되므로 기존에 따로 선언했던 리스트는 지워주기!
 * getItemCount()를 필수적으로 오버라이드 할 필요가 없으니 해당 메소드를 삭제
 * 데이터 항목 가져올 때는 getItem(position) 를 사용해서 가져오도록 한다.
 */

class MyAdapter: PagingDataAdapter<String, MyViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<String>() {
            override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item, null)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvTitle.text = getItem(position)
    }

}

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val tvTitle = itemView.findViewById<TextView>(R.id.tv_item)

}