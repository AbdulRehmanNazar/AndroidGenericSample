package com.ar.sample.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.ar.sample.data.models.GithubContributors
import com.ar.sample.databinding.ActivityMainBinding
import com.ar.sample.interfaces.ItemClickListener
import com.ar.sample.recievers.ADBCommandReceiver
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity(), OnRefreshListener {
    private val TAG = MainActivity::class.java.simpleName
    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var adbCommandReceiver: ADBCommandReceiver
    private lateinit var viewBinding: ActivityMainBinding

    private val contributerAdapter by lazy {
        GithubContributorAapter(this@MainActivity)
    }

    private val requestLocationPerm = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Permission is granted")
        } else {
            Log.d(TAG, "Permission is denied")
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)
        init()
        Log.i("MainActivity", "MainActivity Created")
    }

    private fun init() {
//        checkLocationPermission()
        setupObserver()
        viewBinding.swipeRefresh.setOnRefreshListener(this)
        viewBinding.repoUsersRV.setLayoutManager(LinearLayoutManager(this));
        viewBinding.repoUsersRV.adapter = contributerAdapter
    }

    /**
     * Observer the live Changes in data in ViewModel
     */
    private fun setupObserver() {
        mainViewModel.contributerList.observe(this) { contributerList ->
            contributerAdapter.submitList(contributerList)
        }
        mainViewModel.showLoadingBar.observe(this) { isLoading ->
            if (isLoading) {
                viewBinding.progressBar.visibility = View.VISIBLE
            } else {
                viewBinding.progressBar.visibility = View.GONE
            }
        }
        contributerAdapter.setClickListener(object : ItemClickListener<GithubContributors> {
            override fun onItemClick(item: GithubContributors, type: String) {
                Toast.makeText(this@MainActivity, "Item Clicked: ${item.name}", Toast.LENGTH_SHORT)
                    .show()
            }
        })
    }

    @SuppressLint("InlinedApi")
    private fun setupReceiver() {
        adbCommandReceiver = ADBCommandReceiver()
        val filter = IntentFilter().apply {
            addAction("io.lorikeet.ENCRYPT_AND_STORE")
            addAction("io.lorikeet.AUTH_TEST")
        }
        registerReceiver(adbCommandReceiver, filter, RECEIVER_EXPORTED)
    }

    /**
     *  Check Location Permission
     */
    private fun checkLocationPermission() {
        Log.d("GPSService", "checkLocationPermission")
        if (ActivityCompat.checkSelfPermission(
                this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestLocationPerm.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        } else {
            Log.d(TAG, "Location is already granted")
        }
    }

    override fun onRefresh() {
        mainViewModel.fetchRemoteContributers()
    }

    override fun onDestroy() {
        super.onDestroy()
//        unregisterReceiver(adbCommandReceiver)
    }
}
