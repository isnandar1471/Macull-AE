package com.example.maculae

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import com.example.maculae.activity.DaftarKulinerUser
import com.example.maculae.activity.DaftarKulinerAdmin
import com.example.maculae.activity.LoginActivity
import com.example.maculae.fragment.TambahKuliner
import com.example.maculae.helper.Constant
import com.example.maculae.helper.PreferenceHelper
import com.example.senthil.kotlin_navigationdrawer.Fragment.HomeFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.beranda.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var sharedpref: PreferenceHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)



        sharedpref = PreferenceHelper(this)

        getSupportActionBar()?.setDisplayShowTitleEnabled(false)
        getSupportActionBar()?.title = ""

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        displayScreen(-1)
        supportFragmentManager.beginTransaction().replace(R.id.relativelayout, HomeFragment()).commit()


    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.activity_main_drawer, menu)
//        return true
//    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }
    fun displayScreen(id: Int){

        when (id){
            R.id.nav_beranda -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, HomeFragment()).commit()
            }

            R.id.profil -> {
                showProfil()
            }

            R.id.nav_tambah_kuliner -> {
                supportFragmentManager.beginTransaction().replace(R.id.relativelayout, TambahKuliner()).commit()
            }

            R.id.nav_daftar_kuliner -> {
                moveInCheckSession()
            }

            R.id.nav_logout -> {
                val em = sharedpref.getString(Constant.PREF_EMAIL)
                sharedpref.clear()
                Toast.makeText(
                    this@MainActivity,
                    em + " Log Out",
                    Toast.LENGTH_LONG
                ).show()

                startActivity(Intent(this,LoginActivity::class.java))
                finish()
            }

        }
    }
    
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        displayScreen(item.itemId)
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    /**
     * ini adalah fungsi untuk menentukan level yang sedang login
     */
    fun moveInCheckSession(){
        if (sharedpref.getString(Constant.PREF_LEVEL) == "admin"){
            startActivity(Intent(this,DaftarKulinerAdmin::class.java))
        }else{
            startActivity(Intent(this,DaftarKulinerUser::class.java))
        }
    }

    /**
     * ini adalah fungsi untuk menampilkan profil yang login
     */
    private fun showProfil(){
        lateinit var dialog: AlertDialog
        val builder = AlertDialog.Builder(this)
        var txtMsg =    "Username   : ${ sharedpref.getString(Constant.PREF_USERNAME)}  \n" +
                        "Email      : ${sharedpref.getString(Constant.PREF_EMAIL)}      \n" +
                        "Level      : ${sharedpref.getString(Constant.PREF_LEVEL)}          "
        builder.setTitle("Profil")
        builder.setMessage(txtMsg)
//        val dialogClickListener = DialogInterface.OnClickListener{ _, which ->
//            when(which){
//                DialogInterface.BUTTON_POSITIVE -> {
////                    startActivity(Intent(this,DaftarKulinerUser::class.java))
//                }
//                DialogInterface.BUTTON_NEUTRAL -> {
////                    startActivity(Intent(this,DaftarKulinerAdmin::class.java))
//                }
//
//            }
//        }
        builder.setOnCancelListener( DialogInterface.OnCancelListener {} )
        dialog = builder.create()
        dialog.show()
    }
}

