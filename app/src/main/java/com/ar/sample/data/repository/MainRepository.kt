package com.ar.sample.data.repository

import android.util.Log
import com.ar.sample.data.remote.api.APIService
import com.ar.sample.data.models.Resource
import com.ar.sample.data.local.db.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject


/**
 * @Author: Abdul Rehman
 */

class MainRepository @Inject constructor(
    private val apiService: APIService,
    private val appDatabase: AppDatabase
) {

    fun getLocalDBData() = appDatabase.contributersDao().getAllContributers()
    /**
     * getSchedule from server and save to local database implementation
     */
    suspend fun getGithubContributers() = flow {
        try {
            emit(Resource.Loading(null))
            apiService.getRepoContributer().let { response ->
                if (response.isSuccessful) {
                    val resultData = response.body()
                    if (resultData == null) {
                        emit(Resource.Error("Result was null"))
                        Log.e("ScheduleService", "Result was null")
                    } else {
                        appDatabase.contributersDao().deleteAllAndInsertList(resultData, true)
                        emit(Resource.Success(ArrayList(response.body())))
                    }
                } else {
                    emit(Resource.Error(response.message()))
                    Log.e("ScheduleService", "Error occurred: ${response.message()}")
                }
            }

        } catch (e: Exception) {
            emit(Resource.Error(e.message.toString()))
            e.printStackTrace()
        }
    }.flowOn(Dispatchers.IO)

}