package com.suthaw.restaurnat.rest

import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.rest.Config
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET(Config.users)
    fun getGithubUsers(@Query("since") id: Int?,
                       @Query("pagesize") pagesize:Int): Call<List<GithubUser>>

    @GET(Config.user_by_name)fun getSingleGithubUser
            (@Path("user_name") username :String?): Call<GithubUser>

}
