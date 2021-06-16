package com.madlab.medicinedb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.madlab.medicinedb.databinding.ActivityMainBinding
import com.madlab.medicinedb.db.MedicineModel

class MainActivity : AppCompatActivity() {

    private lateinit var controller: NavController

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.newmed.setOnClickListener {
            new()
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        controller = navHostFragment.navController
        setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(this, controller)
    }

    private fun new() {
        val nav = MedicineFragmentDirections.actionMedicineFragmentToAddFragment()
        controller.navigate(nav)
    }


    fun fabVisibility(vis: Int){
        when(vis){
            View.VISIBLE -> binding.appbar.setExpanded(true)
            View.GONE -> binding.appbar.setExpanded(false)
        }
        binding.newmed.visibility = vis
    }

    fun gotoHome() {
        controller.popBackStack()
    }

    val db by lazy { (application as MApplication).getDB() }
}