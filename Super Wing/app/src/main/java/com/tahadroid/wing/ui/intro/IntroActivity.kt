package com.tahadroid.wing.ui.intro

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager.widget.ViewPager
import com.google.firebase.auth.FirebaseAuth
import com.tahadroid.wing.R
import com.tahadroid.wing.ui.intro.introfragments.FindFragment
import com.tahadroid.wing.ui.intro.introfragments.HotFragment
import com.tahadroid.wing.ui.intro.introfragments.ReceiveFragment
import com.tahadroid.wing.ui.intro.signin.SignInActivity
import com.tahadroid.wing.ui.splash.SplashActivity
import kotlinx.android.synthetic.main.activity_intro.*


class IntroActivity : AppCompatActivity() {
    private var selectPage: Int = 0
    private lateinit var adapterPager: MyPagerAdapter
    private val mAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)
        adapterPager =
            MyPagerAdapter(
                supportFragmentManager
            )
        view_pager.adapter = adapterPager
        flexibleIndicator.initViewPager(view_pager)
        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {

            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {


            }

            override fun onPageSelected(position: Int) {
                selectPage = position
                if (position == 0) {
                    nextButton.setText("Next")
                } else if (position == 1) {
                    nextButton.setText("Next")
                } else if (position == 2) {
                    nextButton.setText("Finish")
                }
            }

        })


        nextButton.setOnClickListener {
            when (it.id) {
                R.id.nextButton ->
                    if (selectPage == 0) {
                        view_pager.currentItem = 1
                    } else if (selectPage == 1) {
                        view_pager.currentItem = 2
                    } else if (selectPage == 2) {
                        startActivity(Intent(this@IntroActivity, SignInActivity::class.java))
                        finish()
                    }
            }
        }

    }

    private class MyPagerAdapter(supportFragmentManager: FragmentManager) :
        FragmentPagerAdapter(supportFragmentManager) {
        private val NUM_iTEMS = 3
        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return FindFragment()
                1 -> return HotFragment()
                2 -> return ReceiveFragment()
                else -> return null!!
            }
        }

        override fun getCount(): Int {
            return NUM_iTEMS
        }

        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            val fragment = super.instantiateItem(container, position)
            return fragment
        }

        override fun getPageTitle(position: Int): CharSequence? {
            return "page" + position
        }
    }

    override fun onStart() {
        super.onStart()

        if (mAuth.currentUser?.uid != null) {
            val intentMianActivity = Intent(this@IntroActivity, SplashActivity::class.java)
            startActivity(intentMianActivity)
        }
    }

}