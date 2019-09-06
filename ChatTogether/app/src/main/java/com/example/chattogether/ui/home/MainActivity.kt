package com.example.chattogether.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.chattogether.R
import com.example.chattogether.databinding.ActivityMainBinding
import com.example.chattogether.ui.AddFriendActivity
import com.example.chattogether.ui.BaseActivity
import com.example.chattogether.util.jump2Activity
import com.google.android.material.tabs.TabLayout

class MainActivity : BaseActivity<ActivityMainBinding>() {


    private val tableTitle = arrayOf("尬聊", "通讯录", "设置")
    private val tableTitleIcon = arrayOf(
        R.drawable.message_selector,
        R.drawable.contacts_selector,
        R.drawable.settings_selector
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
    }

    override fun initView() {
        initDatabinding(R.layout.activity_main)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        val toolbar: Toolbar = dataBinding.includeMain.toolbar
        setSupportActionBar(toolbar)
        val tabLayout = dataBinding.includeMain.includeAppbar.tabLayout
        val viewPager = dataBinding.includeMain.includeAppbar.viewpager
        viewPager.adapter = HomeFragmentpagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        for (i in 0 until 3) {
            tabLayout.getTabAt(i)!!.customView = getView(tableTitle[i], tableTitleIcon[i])
        }
        tabLayout.setOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
                p0?.let {
                    val tv = it.customView?.findViewById<TextView>(R.id.tv)
                    tv?.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.textColorGray
                        )
                    )
                    val iv = it.customView?.findViewById<ImageView>(R.id.image)
                    iv?.isSelected = false
                }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    val tv = it.customView?.findViewById<TextView>(R.id.tv)
                    tv?.setTextColor(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.textColorDark
                        )
                    )
                    val iv = it.customView?.findViewById<ImageView>(R.id.image)
                    iv?.isSelected = true
                }
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_friend -> {
                jump2Activity(this, AddFriendActivity::class.java)
            }
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        return true
    }

    fun getView(title: String, image: Int): View {
        val view = LayoutInflater.from(this).inflate(R.layout.item_tab, null)
        val imageView = view.findViewById<ImageView>(R.id.image)
        val textView = view.findViewById<TextView>(R.id.tv)
        imageView.background = ContextCompat.getDrawable(this, image)
        textView.text = title
        return view
    }

}
