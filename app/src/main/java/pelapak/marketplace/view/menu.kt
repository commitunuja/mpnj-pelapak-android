package pelapak.marketplace.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_menu.*
import pelapak.marketplace.R
import pelapak.marketplace.fragment.beranda
import pelapak.marketplace.fragment.history
import pelapak.marketplace.fragment.setting

class menu : AppCompatActivity() {
    var selectedFragment: Fragment? = null
    var click : Int = 0
    var validasi_click : Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)
        navigation.itemIconTintList = null
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        inflateFragment(beranda())

    }
    override fun onBackPressed() {
        if (selectedFragment is beranda) {
            super.onBackPressed()
        } else {
            navigation.selectedItemId = R.id.navigation_home
            click = 0
            validasi_click = 0
            inflateFragment(beranda())
        }
    }

    private val mOnNavigationItemSelectedListener =
        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    click = 1
                    if (click != validasi_click){
                        validasi_click = 1
                        inflateFragment(beranda())
                        return@OnNavigationItemSelectedListener true
                    }
                }
                R.id.navigation_history -> {
                    click = 2
                    if (click != validasi_click){
                        validasi_click = 2
                        inflateFragment(history())
                        return@OnNavigationItemSelectedListener true

                    }
                }
                R.id.navigation_settings -> {
                    click = 3
                    if (click != validasi_click){
                        validasi_click = 3
                        inflateFragment(setting())
                        return@OnNavigationItemSelectedListener true

                    }

                }
            }
            false
        }

    private fun inflateFragment(fragment: Fragment) {
        selectedFragment = fragment
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame_content, fragment)/*.addToBackStack(null)*/.commit()
    }
}
