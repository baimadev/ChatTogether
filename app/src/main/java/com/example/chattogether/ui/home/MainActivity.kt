package com.example.chattogether.ui.home


import android.Manifest.permission.CAMERA
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewpager.widget.ViewPager
import cn.bmob.v3.datatype.BmobFile
import cn.bmob.v3.helper.RequestHelper
import com.bumptech.glide.Glide
import com.example.chattogether.R
import com.example.chattogether.bmobapi.BmobUserApi
import com.example.chattogether.data.User
import com.example.chattogether.databinding.ActivityMainBinding
import com.example.chattogether.ui.searchFriend.AddFriendActivity
import com.example.chattogether.ui.BaseActivity
import com.example.chattogether.util.*
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayout
import com.linchaolong.android.imagepicker.ImagePicker
import com.linchaolong.android.imagepicker.cropper.CropImage
import com.linchaolong.android.imagepicker.cropper.CropImageView
import de.hdodenhof.circleimageview.CircleImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.File
import java.security.Permission

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private val tableTitle = arrayOf("尬聊", "通讯录", "设置")
    private val tableTitleIcon = arrayOf(
        R.drawable.message_selector,
        R.drawable.contacts_selector,
        R.drawable.setting_selector
    )
    private val requestPermissionHelper by lazy { RequestPermissionHelper(this) }
    private lateinit var toolbar: Toolbar
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    private lateinit var navView: NavigationView
    internal lateinit var imageAvatar: CircleImageView
    internal var user: User? = null
    private var imagePicker: ImagePicker = ImagePicker().apply { setCropImage(true) }
    private val imagePickerCallback = object : ImagePicker.Callback() {
        override fun onPickImage(imageUri: Uri?) {

        }
        //裁剪图片回调
        override fun onCropImage(imageUri: Uri?) {
            super.onCropImage(imageUri)
            imageUri?.let {
                val file = File(it.path!!)
                val bmobFile = BmobFile(file)
                user?.run {
                    bmobFile.obtain(file.name, "0", file.toURL().toString())
                    avatar = bmobFile
                    BmobUserApi.updataUser(this)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { t ->
                            log(t.message!!)
                        }
                }
                Glide.with(this@MainActivity)
                    .load(bmobFile.localFile)
                    .centerCrop()
                    .dontAnimate()
                    .error(R.drawable.avatar_default)
                    .into(imageAvatar)
            }
        }

        //自定义裁剪设置
        override fun cropConfig(builder: CropImage.ActivityBuilder?) {
            super.cropConfig(builder)
            builder?.apply {
                setMultiTouchEnabled(false)
                    .setGuidelines(CropImageView.Guidelines.OFF)
                    .setCropShape(CropImageView.CropShape.OVAL)
                    .setRequestedSize(500, 500)
            }
        }
        //用户拒绝授权回调
        override fun onPermissionDenied(
            requestCode: Int,
            permissions: Array<out String>?,
            grantResults: IntArray?
        ) {
            super.onPermissionDenied(requestCode, permissions, grantResults)
            AlertDialog.Builder(this@MainActivity)
                .setTitle("提示")
                .setMessage("更换头像需要相机权限,请前往设置界面打开权限！")
                .setPositiveButton("知道了") { dialogInterface, i ->
                    dialogInterface.dismiss()
                }
                .show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        initBinding(R.layout.activity_main)
        super.onCreate(savedInstanceState)
        requestPermissionHelper.requestPermissions(
            arrayOf(CAMERA, WRITE_EXTERNAL_STORAGE),
            RequestPermissionHelper.PERMISSION_REQUEST_CODE
        )
    }

    override fun initView() {
        user = BmobUserApi.getCurrentUser()
        navView = binding.navView
        toolbar = binding.includeMain.toolbar
        setSupportActionBar(toolbar)
        tabLayout = binding.includeMain.includeAppbar.tabLayout
        viewPager = binding.includeMain.includeAppbar.viewpager
        viewPager.adapter = HomeFragmentpagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        setCustomTabLayout()
        val headView = navView.getHeaderView(0)
        val nicknameText = headView.findViewById<TextView>(R.id.nickname_text)
        nicknameText.text = user?.nickname
        imageAvatar = headView.findViewById(R.id.imageView_avatar)
        initAvatar(imageAvatar)
        onNavigationItemSelected()
    }

    /**
     * NavigationItem点击事件
     */
    private fun onNavigationItemSelected(){
        navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_camera -> {
                    if(requestPermissionHelper.checkPermission(CAMERA)){
                            requestPermission()
                    }else{
                        imagePicker.startCamera(this, imagePickerCallback)
                    }
                }
                R.id.nav_gallery -> {
                    if(requestPermissionHelper.checkPermission(CAMERA)){
                        requestPermission()
                    }else{
                        imagePicker.startGallery(this, imagePickerCallback)
                    }
                }
            }
            true
        }
    }
    private fun requestPermission(){
        if(requestPermissionHelper.checkIsDeny(CAMERA)){
            //前往设置界面
            Dialog.createDialog(
                this,
                "提示",
                "请前往设置界面，允许APP获取权限",
                "前往",
                object : DialogButtonClickListener {
                    override fun onClickButton(dialog: DialogInterface) {
                        startActivity(requestPermissionHelper.getAppDetailSettingIntent())
                        dialog.dismiss()
                    }
                },
                "取消",
                object : DialogButtonClickListener {
                    override fun onClickButton(dialog: DialogInterface) {
                        dialog.dismiss()
                    }
                }).show()
        }else{
            //直接申请
            requestPermissionHelper.requestPermission(CAMERA,RequestPermissionHelper.PERMISSION_REQUEST_CODE_CAMERA)
        }

    }
    //初始化头像
    private fun initAvatar(imageView: CircleImageView) {
        user?.let {
            Glide.with(this)
                .load(it.avatar?.url)
                .placeholder(R.drawable.avatar_default)
                .into(imageAvatar)
        }
    }

    /**
     *  设置自定义tablayout
     */
    private fun setCustomTabLayout() {
        for (i in 0 until 3) {
            val itemTab = this.layoutInflater.inflate(R.layout.item_tab, null, false)
            val imageView = itemTab.findViewById<ImageView>(R.id.iv_tab)
            val textView: TextView = itemTab.findViewById(R.id.tv_tab)
            textView.text = tableTitle[i]
            imageView.background = ContextCompat.getDrawable(this, tableTitleIcon[i])
            tabLayout.getTabAt(i)!!.customView = itemTab
        }
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {}
            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.let {
                    val imageView = it.customView!!.findViewById<ImageView>(R.id.iv_tab)
                    val textView = it.customView!!.findViewById<TextView>(R.id.tv_tab)
                    imageView.isSelected = false
                    textView.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.textColorGray
                        )
                    )
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    val imageView = it.customView!!.findViewById<ImageView>(R.id.iv_tab)
                    val textView = it.customView!!.findViewById<TextView>(R.id.tv_tab)
                    imageView.isSelected = true
                    textView.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.textColorDark
                        )
                    )
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_add -> {
                jump2Activity(this, AddFriendActivity::class.java)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        imagePicker.onActivityResult(this, requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        imagePicker.onRequestPermissionsResult(this, requestCode, permissions, grantResults)
        when (requestCode) {
            RequestPermissionHelper.PERMISSION_REQUEST_CODE_CAMERA -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    log("请求camera权限成功")
                } else {
                    log("请求camera权限失败")
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        BmobUserApi.connect()
    }


}
