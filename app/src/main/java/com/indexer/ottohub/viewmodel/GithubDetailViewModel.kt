package com.indexer.ottohub.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.rest.RestClient
import com.indexer.ottohub.rest.enqueue
import android.arch.lifecycle.MutableLiveData


class GithubDetailViewModel(application: Application) : AndroidViewModel(application) {

    private var githubUser: MutableLiveData<GithubUser>? = null


    fun getGithubUser(username: String?) : MutableLiveData<GithubUser>? {
        githubUser = MutableLiveData()
        loadItem(username)
        return githubUser
    }

    private fun loadItem(username: String?) {
        val singleUser = RestClient.getService().getSingleGithubUser(username)
        singleUser.enqueue(success = {
           githubUser?.postValue(it.body())
        }, failure = {

        })
    }
}
