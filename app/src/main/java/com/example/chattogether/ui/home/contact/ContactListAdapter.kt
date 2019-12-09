package com.example.chattogether.ui.home.contact


import com.example.chattogether.databinding.ItemFriendsListBinding
import com.example.chattogether.ui.BaseRecyclerViewAdapter
import com.example.chattogether.viewmodels.UserViewModel


class ContactListAdapter(
    dataList: ArrayList<UserViewModel>,
    layoutId: Int,
    listener: BaseRecyclerViewListener?=null
) : BaseRecyclerViewAdapter<ItemFriendsListBinding>(dataList,layoutId,listener)