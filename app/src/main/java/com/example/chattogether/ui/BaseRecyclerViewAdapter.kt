package com.example.chattogether.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView


abstract class BaseRecyclerViewAdapter< E : ViewDataBinding>(private val dataList: ArrayList<out Any>,private val layoutId:Int,val listener:BaseRecyclerViewListener?=null) :
    RecyclerView.Adapter<BaseRecyclerViewAdapter<E>.ViewHolder<E>>() {
    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder<E>, position: Int) {
        val data = dataList[position]
        holder.bind(data)
        holder.setListener(position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<E> {
        val inflater = LayoutInflater.from(parent.context)
        lateinit var viewHolder: RecyclerView.ViewHolder
        val binding: E = DataBindingUtil.inflate(inflater,layoutId, parent, false)
        viewHolder = ViewHolder(binding.root, binding)
        return viewHolder
    }

    inner class ViewHolder<E : ViewDataBinding>(itemView: View, val binding: E) :
        RecyclerView.ViewHolder(itemView) {
         fun bind(item:Any){
            listener?.onBind(item,binding)
            binding.executePendingBindings()
        }
        fun setListener(pos:Int){
            itemView.setOnClickListener {
                listener?.OnItemClickListener(itemView,pos)
            }
        }
    }
    interface BaseRecyclerViewListener{
        fun onBind(item:Any,binding: ViewDataBinding)
        fun OnItemClickListener(view:View,pos:Int)
    }
}