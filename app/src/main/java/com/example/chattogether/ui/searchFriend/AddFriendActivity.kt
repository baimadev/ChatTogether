package com.example.chattogether.ui.searchFriend

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.newim.bean.BmobIMUserInfo
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobFriendApi
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.data.User
import com.example.chattogether.databinding.ActivityAddFriendBinding
import com.example.chattogether.databinding.ItemSearchFriendBinding
import com.example.chattogether.ui.BaseActivity
import com.example.chattogether.ui.BaseRecyclerViewAdapter
import com.example.chattogether.ui.home.MainActivity
import com.example.chattogether.util.InjectorUtils
import com.example.chattogether.util.jump2Activity
import com.example.chattogether.util.toast
import com.example.chattogether.viewmodels.UserViewModel
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class AddFriendActivity : BaseActivity<ActivityAddFriendBinding>() {
    private lateinit var currentUser: User
    override fun initView() {
        binding.toolbarAddFriend.apply {
            title = "添加新朋友"
            setTitleTextColor(ContextCompat.getColor(this@AddFriendActivity, R.color.lightWhite))
            setSupportActionBar(this)
            navigationIcon = ContextCompat.getDrawable(this@AddFriendActivity, R.drawable.icon_left)
            setNavigationOnClickListener {
                jump2Activity(this@AddFriendActivity, MainActivity::class.java)
                finish()
            }
        }
        queryUserList()
        binding.recyclerSearch.apply {
            adapter = SearchRecyclerViewAdapter(
                dataList,
                R.layout.item_search_friend,
                object : BaseRecyclerViewAdapter.BaseRecyclerViewListener {
                    override fun OnItemClickListener(view: View, pos: Int) {
                        addFriend(pos)
                    }

                    override fun onBind(item: Any, binding: ViewDataBinding) {
                        val user = item as UserViewModel
                        val itemBinding = binding as ItemSearchFriendBinding
                        itemBinding.user = user
                    }
                })
            layoutManager = LinearLayoutManager(this@AddFriendActivity)
        }
        BmobUserApi.getCurrentUser()?.apply { currentUser = this }
    }

    //用户列表
    var dataList = ArrayList<UserViewModel>()

    //查询用户列表
    @SuppressLint("CheckResult")
    fun queryUserList() {
        BmobUserApi.queryUser2Rx()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .filter {
                currentUser.objectId != it.objectId
            }
            .doOnNext {
                dataList.add((InjectorUtils.provideUserViewModelFactory(it).create(UserViewModel::class.java)))
            }
            .subscribe({
            }, {
                toast(it.message!!)
            })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initBinding(R.layout.activity_add_friend)
        super.onCreate(savedInstanceState)
    }

    fun addFriend(pos: Int) {
        val user = dataList[pos]
        val friendInfo = BmobIMUserInfo()
        friendInfo.userId = user.user.objectId
        friendInfo.name = user.user.username
        friendInfo.avatar=user.avatarUrl
        BmobFriendApi.sendAddFriendMessage(friendInfo)
    }


}
