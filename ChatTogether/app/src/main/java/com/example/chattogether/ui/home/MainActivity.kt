package com.example.chattogether.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.chattogether.R
import com.example.chattogether.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private val tableTitle= arrayOf("尬聊","通讯录","设置")
    private val tableTitleIcon= arrayOf(R.drawable.message_selector,R.drawable.contacts_selector,R.drawable.settings_selector)
    lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_main)
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        initView()

    }

    private fun initView(){
        window.statusBarColor=ContextCompat.getColor(this,
            R.color.colorPrimary
        )
        val toolbar: Toolbar = binding.includeMain.toolbar
        setSupportActionBar(toolbar)
        val tabLayout=binding.includeMain.includeAppbar.tabLayout
        val viewPager=binding.includeMain.includeAppbar.viewpager
        viewPager.adapter=HomeFragmentpagerAdapter(supportFragmentManager)
        tabLayout.setupWithViewPager(viewPager)
        for(i in 0 until 3){
            tabLayout.getTabAt(i)!!.customView = getView(tableTitle[i],tableTitleIcon[i])
        }
        tabLayout.setOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabReselected(p0: TabLayout.Tab?) {
            }

            override fun onTabUnselected(p0: TabLayout.Tab?) {
               p0?.let {
                   val tv=it.customView?.findViewById<TextView>(R.id.tv)
                   tv?.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.textColorGray))
                   val iv=it.customView?.findViewById<ImageView>(R.id.image)
                   iv?.isSelected=false
               }
            }

            override fun onTabSelected(p0: TabLayout.Tab?) {
                p0?.let {
                    val tv=it.customView?.findViewById<TextView>(R.id.tv)
                    tv?.setTextColor(ContextCompat.getColor(this@MainActivity,R.color.textColorDark))
                    val iv=it.customView?.findViewById<ImageView>(R.id.image)
                    iv?.isSelected=true
                }
            }
        })
      //  toast(InjectorUtils.getUserRepository(this).getUsers().get(0).username+InjectorUtils.getUserRepository(this).getUsers().get(0).username)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
      return true
    }
    fun getView(title:String,image:Int):View{
        val view=LayoutInflater.from(this).inflate(R.layout.item_tab,null)
        val imageView=view.findViewById<ImageView>(R.id.image)
        val textView=view.findViewById<TextView>(R.id.tv)
        imageView.background=ContextCompat.getDrawable(this,image)
        textView.text=title
        return view
    }

}
