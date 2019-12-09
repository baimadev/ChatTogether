package com.example.chattogether.ui

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import cn.bmob.newim.bean.BmobIMMessage
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobFriendApi
import com.example.chattogether.data.NewFriend
import com.example.chattogether.databinding.ItemAgreeAddFriendListBinding
import com.example.chattogether.databinding.ItemFriendListBinding
import com.example.chattogether.util.Dialog
import com.example.chattogether.util.DialogButtonClickListener


class MultiLayoutAdapter(
    private val dataList: ArrayList<NewFriend>,
    val context: Context
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = dataList[position]
        if(data.type== FIRST_TYPE){
            val firstHolder=holder as FirstViewHolder
            firstHolder.binding.friend=data
            firstHolder.itemView.setOnClickListener {
                firstItemClick(data)
            }
        }else{
            val secondHolder=holder as SecondViewHolder
            secondHolder.binding.friend=data

        }

    }

    private fun firstItemClick (data:NewFriend){
        val friendNickname = data.nickname
        Dialog.createDialog(
            context,
            "${friendNickname}请求添加你为好友!",
            "",
            "同意",
            object : DialogButtonClickListener {
                override fun onClickButton(dialog: DialogInterface) {
                    BmobFriendApi.sendAgreeAddFriendMessage(data, object :
                        SaveListener<BmobIMMessage>() {
                        override fun done(p0: BmobIMMessage?, p1: BmobException?) {
                            //存入好友表
                            BmobFriendApi.agreeAdd(data)
                        }
                    })
                }
            },
            "丑拒",
            object : DialogButtonClickListener {
                override fun onClickButton(dialog: DialogInterface) {
                    //TODO List不再显示
                    dialog.dismiss()
                }
            }
        ).show()
    }
    companion object{
        const val FIRST_TYPE=123
        const val SECOND_TYPE=456
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun getItemViewType(position: Int): Int {
        return dataList[position].type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        lateinit var viewHolder: RecyclerView.ViewHolder
        if(viewType== FIRST_TYPE){
            val binding:ItemFriendListBinding=DataBindingUtil.inflate(inflater, R.layout.item_friend_list,parent,false)
            viewHolder=FirstViewHolder(binding.root,binding)
        }else if(viewType==SECOND_TYPE){
            val binding:ItemAgreeAddFriendListBinding=DataBindingUtil.inflate(inflater, R.layout.item_agree_add_friend_list,parent,false)
            viewHolder=SecondViewHolder(binding.root,binding)
        }
        return viewHolder
    }

    inner class FirstViewHolder(itemView: View,val binding:ItemFriendListBinding) :
        RecyclerView.ViewHolder(itemView)
    inner class SecondViewHolder(itemView: View,val binding:ItemAgreeAddFriendListBinding) :
        RecyclerView.ViewHolder(itemView)


}