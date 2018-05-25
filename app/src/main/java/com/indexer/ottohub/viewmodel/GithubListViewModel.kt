package com.indexer.ottohub.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.indexer.ottohub.model.GithubUser
import com.suthaw.restaurnat.rest.RestClient
import com.suthaw.restaurnat.rest.enqueue
import android.arch.lifecycle.MutableLiveData


class GithubListViewModel(application: Application) : AndroidViewModel(application) {

    private var userList: MutableLiveData<List<GithubUser>>? = null
    private var lastId :MutableLiveData<Boolean>? = null


    fun getLastIdOrNot(boolean: Boolean): LiveData<Boolean>? {
        lastId = MutableLiveData()
        lastId?.value = boolean
        return lastId
    }

    fun getGithubUser(lastId: Int) : LiveData<List<GithubUser>>? {
        userList = MutableLiveData()
        loadItem(lastId)
        return userList
    }

    private fun loadItem(lastId: Int) {
        val user = RestClient.getService().getGithubUsers(lastId, 20)
        user.enqueue(success = {
            if (it.isSuccessful) {
                userList?.postValue(it.body())
            }
        }, failure = {
        })
    }
}
