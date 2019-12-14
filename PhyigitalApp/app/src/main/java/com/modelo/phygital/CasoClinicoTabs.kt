package com.modelo.phygital

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.navigation.ui.AppBarConfiguration
import com.modelo.phygital.tabs.ModelosFragment
import com.modelo.phygital.tabs.NotasFragment
import com.modelo.phygital.tabs.SesionesFragment
import kotlinx.android.synthetic.main.activity_caso_clinico_tabs.*
import kotlinx.android.synthetic.main.app_bar_sesiones.*

class CasoClinicoTabs : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_caso_clinico_tabs)

        val toolbar: Toolbar = findViewById(R.id.bar_sesiones_contenido)

         setSupportActionBar(toolbar)

        val adapter = MyViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(SesionesFragment() , " Sesiones ")
        adapter.addFragment(NotasFragment() , " Notas ")
        adapter.addFragment(ModelosFragment() , " Modelos ")
        viewPager.adapter = adapter
        tabs.setupWithViewPager(viewPager)
    }


    class MyViewPagerAdapter(manager: FragmentManager) : FragmentPagerAdapter(manager){


       private val fragmentList : MutableList<Fragment> =ArrayList()
        private val titleList : MutableList<String> =ArrayList()

        override fun getItem(position: Int): Fragment {

            return fragmentList[position]
        }

        override fun getCount(): Int {

            return fragmentList.size
        }

        fun addFragment(fragment: Fragment,title:String){
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return titleList[position]
        }


    }
}
