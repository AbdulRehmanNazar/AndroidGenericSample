package com.ar.sample.data.remote.api

import com.ar.sample.data.models.GithubContributors
import retrofit2.Response
import retrofit2.http.GET


/**
 * @Author: Abdul Rehman
 */
interface APIService {
    @GET("repos/ruby/ruby/contributors")
    suspend fun getRepoContributer(): Response<List<GithubContributors>>
}