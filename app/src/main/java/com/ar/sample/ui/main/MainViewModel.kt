package com.ar.sample.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ar.sample.data.local.localdatasource.ContributersDAO
import com.ar.sample.data.models.GithubContributors
import com.ar.sample.data.models.Resource
import com.ar.sample.data.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import kotlin.math.truncate


/**
 * @Author: Abdul Rehman
 */

@HiltViewModel
class MainViewModel @Inject constructor(
    private val mainRepository: MainRepository

) : ViewModel() {
    private val TAG = MainViewModel::javaClass.name

    private val _contributerList = MutableLiveData(ArrayList<GithubContributors>())
    val contributerList: LiveData<ArrayList<GithubContributors>>
        get() = _contributerList

    private val _showLoadingBar = MutableLiveData(false)
    val showLoadingBar: LiveData<Boolean>
        get() = _showLoadingBar

    init {
        fetchRemoteContributers()
    }

    /**
     * Get Schedule data from remote
     */
    fun fetchRemoteContributers() {
        viewModelScope.launch(Dispatchers.IO) {
            mainRepository.getGithubContributers().collectLatest { resource ->
                when (resource) {
                    is Resource.Success -> {
                        _contributerList.postValue(resource.data)
                        _showLoadingBar.postValue(false)
                    }

                    is Resource.Error -> {
                        Log.e(TAG, "fetchRemoteContributers: ${resource.message}")
                        _showLoadingBar.postValue(false)
                    }

                    is Resource.Loading -> {
                        _showLoadingBar.postValue(true)
                    }
                }
            }
        }
    }
}