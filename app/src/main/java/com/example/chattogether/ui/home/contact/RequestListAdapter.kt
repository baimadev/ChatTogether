package com.example.chattogether.ui.home.contact

import com.example.chattogether.data.NewFriend
import com.example.chattogether.databinding.ItemFriendListBinding
import com.example.chattogether.ui.BaseRecyclerViewAdapter


class RequestListAdapter(
    dataList: ArrayList<NewFriend>,
    layoutId: Int,
    listener: BaseRecyclerViewListener?=null
) : BaseRecyclerViewAdapter<ItemFriendListBinding>(dataList,layoutId,listener)