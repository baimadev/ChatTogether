package com.example.chattogether.ui.searchFriend


import com.example.chattogether.databinding.ItemSearchFriendBinding
import com.example.chattogether.ui.BaseRecyclerViewAdapter
import com.example.chattogether.viewmodels.UserViewModel

class SearchRecyclerViewAdapter(
    dataList: ArrayList<UserViewModel>,
    layoutId: Int,
    lisener: BaseRecyclerViewListener
) : BaseRecyclerViewAdapter<ItemSearchFriendBinding>(dataList, layoutId, lisener)