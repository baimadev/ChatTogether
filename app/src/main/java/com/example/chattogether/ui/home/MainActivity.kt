package com.example.chattogether.ui.home

import android.os.Bundle
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.view.Menu
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.chattogether.R
import com.example.chattogether.databinding.ActivityMainBinding
import com.example.chattogether.util.InjectorUtils
import com.example.chattogether.util.log
import com.example.chattogether.util.toast

class MainActivity : AppCompatActivity() {

    val tableTitle= arrayOf("尬聊","通讯录","设置")
    val tableTitleIcon= arrayOf(R.drawable.message,R.drawable.message,R.drawable.message)
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
            tabLayout.getTabAt(i)!!.text = tableTitle[i]
            tabLayout.getTabAt(i)!!.icon=ContextCompat.getDrawable(this,tableTitleIcon[i])
        }


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

}
