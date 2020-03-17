package com.reryka.cheatpaper

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.reryka.cheatpaper.Constants.Companion.LENGTH
import com.reryka.cheatpaper.Constants.Companion.TAB_NAME
import com.reryka.cheatpaper.Constants.Companion.TEXT_MAIN
import com.reryka.cheatpaper.Constants.Companion.ARG_OBJECT

class MainActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager

    private var fragment: CheatPaperFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPager = findViewById(R.id.view_pager)

        val fragmentManager = supportFragmentManager
        mViewPager.adapter = object : FragmentPagerAdapter(fragmentManager) {

            override fun getItem(i: Int): Fragment {
                fragment = CheatPaperFragment()
                fragment?.arguments = Bundle().apply {
                    putInt(ARG_OBJECT, i)
                }
                return fragment!!
            }

            override fun getCount(): Int {
                return LENGTH
            }

            override fun getPageTitle(position: Int): CharSequence? {
                return TAB_NAME[position]
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_DPAD_RIGHT -> fragment?.nextTab()
            KeyEvent.KEYCODE_DPAD_LEFT -> fragment?.previousTab()
            KeyEvent.KEYCODE_ENTER -> {
            }
            KeyEvent.KEYCODE_BACK -> System.exit(0)
            KeyEvent.KEYCODE_MENU -> {
            }
            else -> return false
        }
        return true
    }
}

class CheatPaperFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val textView: TextView = view.findViewById(R.id.text_view)
        val i = arguments?.getInt(ARG_OBJECT) ?: 0
        textView.text = TEXT_MAIN[i]
    }

    public fun nextTab() {
//        if (tab_layout.selectedTabPosition < tab_layout.tabCount - 1) {
//            tab_layout.getTabAt(tab_layout.selectedTabPosition + 1)!!.select()
//        }
    }

    public fun previousTab() {
//        if (0 < tab_layout.selectedTabPosition) {
//            tab_layout.getTabAt(tab_layout.selectedTabPosition - 1)!!.select()
//        }
    }
}