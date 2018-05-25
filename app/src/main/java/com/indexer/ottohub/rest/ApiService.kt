package com.suthaw.restaurnat.rest

import com.indexer.ottohub.model.GithubUser
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET(Config.users)
    fun getGithubUsers(): Call<List<GithubUser>>
}