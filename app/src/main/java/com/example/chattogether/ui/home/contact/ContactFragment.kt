package com.example.chattogether.ui.home.contact

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import cn.bmob.newim.bean.BmobIMMessage
import cn.bmob.newim.event.MessageEvent
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.SaveListener
import com.example.chattogether.R
import com.example.chattogether.RxBus
import com.example.chattogether.bmobapi.BmobFriendApi
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.bmobapi.Message.AddFriendMessage
import com.example.chattogether.bmobapi.Message.AgreeAddFriendMessage
import com.example.chattogether.data.NewFriend
import com.example.chattogether.databinding.FragmentContactBinding
import com.example.chattogether.databinding.ItemFriendListBinding
import com.example.chattogether.databinding.ItemFriendsListBinding
import com.example.chattogether.ui.BaseRecyclerViewAdapter
import com.example.chattogether.ui.MultiLayoutAdapter
import com.example.chattogether.util.*
import com.example.chattogether.viewmodels.UserViewModel
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ContactFragment : Fragment() {
    lateinit var binding: FragmentContactBinding
    private val friendList = ArrayList<UserViewModel>()
    internal val requestList = ArrayList<NewFriend>()
    lateinit var requestListAdapter: MultiLayoutAdapter
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_contact, container, false)
        initRequestList()
        initFriendList()
        registRxBus()
        registRxBusAgree()
        return binding.root
    }

    private fun initRequestList() {
        requestListAdapter = MultiLayoutAdapter(requestList,context = context!!)
        binding.requestList.run {
            adapter = requestListAdapter
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /**
     * 配置FriendList
     */
    private fun initList() {
        binding.contactList.run {
            adapter = ContactListAdapter(
                friendList,
                R.layout.item_friends_list,
                object : BaseRecyclerViewAdapter.BaseRecyclerViewListener {
                    override fun onBind(item: Any, binding: ViewDataBinding) {
                        val data = item as UserViewModel
                        val rBinding = binding as ItemFriendsListBinding
                        rBinding.friend = data
                    }
                    override fun OnItemClickListener(view: View, pos: Int) {
                        //TODO 打开聊天窗口
                    }
                })
            layoutManager = LinearLayoutManager(activity)
        }
    }

    /*
    查询好友列表
     */
    @SuppressLint("CheckResult")
    fun initFriendList() {
        BmobFriendApi.queryFriends()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .toObservable()
            .flatMap {
                Observable.fromIterable(it)
            }
            .map {
                BmobUserApi.queryUserFromUid(it.friendUser.objectId)
            }
            .flatMap {
                it.toObservable()
            }
            .doFinally { initList() }
            .subscribe({
                val userModel =
                    InjectorUtils.provideUserViewModelFactory(it).create(UserViewModel::class.java)
                friendList.add(userModel)
            }, {
                log(it.message!! + "出了点差错")
            })

    }

    /**
     * 注册Rxbus接受添加好友事件()
     */
    @SuppressLint("CheckResult")
    fun registRxBus() {
        RxBus.instance.toObservable(AddFriendMessage::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var friend: Map<String, String> = HashMap()
                friend = Gson().fromJson(it.extraMsg, friend.javaClass)
                val addFriend = NewFriend(
                    uid = friend["uid"],
                    name = friend["name"],
                    avatar = friend["avatar"],
                    nickname = friend["nickname"]
                )
                addFriend.msg = "${addFriend.nickname}请求添加你为好友！"
                addFriend.type=MultiLayoutAdapter.FIRST_TYPE
                requestList.add(addFriend)
                log("请求")
                requestListAdapter.notifyDataSetChanged()
            }
    }

    /**
     * 注册Rxbus 对方同意添加(未完成 弹框 同意添加后提示)
     */
    @SuppressLint("CheckResult")
    fun registRxBusAgree() {
        RxBus.instance.toObservable(AgreeAddFriendMessage::class.java)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                var friend: Map<String, String> = HashMap()
                friend = Gson().fromJson(it.extraMsg, friend.javaClass)
                val addFriend = NewFriend(
                    uid = friend["uid"],
                    name = friend["name"],
                    avatar = friend["avatar"],
                    nickname = friend["nickname"]
                )
                addFriend.msg = "${addFriend.nickname}已同意添加你为好友！"
                addFriend.type=MultiLayoutAdapter.SECOND_TYPE
                requestList.add(addFriend)
                log("同意")
                requestListAdapter.notifyDataSetChanged()
                BmobFriendApi.agreeAdd(addFriend)
            }
    }

}