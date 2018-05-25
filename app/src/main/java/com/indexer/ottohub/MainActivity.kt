package com.indexer.ottohub

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.suthaw.restaurnat.adapter.GithubUserAdapter
import com.suthaw.restaurnat.rest.RestClient
import com.suthaw.restaurnat.rest.enqueue
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val linearLayoutManager = LinearLayoutManager(this)
        val githubUserAdapter = GithubUserAdapter()
        github_list.let {
            it.layoutManager = linearLayoutManager
            it.adapter = githubUserAdapter
        }

        val user = RestClient.getService().getGithubUsers()
        user.enqueue(success = {
            githubUserAdapter.items = it.body()
        }, failure = {

        })
    }
}
