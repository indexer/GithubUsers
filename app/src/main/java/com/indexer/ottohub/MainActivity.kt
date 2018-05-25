package com.indexer.ottohub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import com.suthaw.restaurnat.adapter.GithubUserAdapter
import com.suthaw.restaurnat.adapter.SpacesItemDecoration
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
            it.addItemDecoration(SpacesItemDecoration(16))
        }

        loadItem(0,githubUserAdapter)

        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                if (!github_list.canScrollVertically(1)){
                    run {
                        loadItem(githubUserAdapter.getItem(lastVisibleItemPosition).id, githubUserAdapter)
                    }
                }
            }
        }

        github_list.addOnScrollListener(recyclerViewOnScrollListener)

    }


    fun loadItem(lastId : Int,adapter: GithubUserAdapter){
        val user = RestClient.getService().getGithubUsers(lastId,20)
        user.enqueue(success = {
            if(it.isSuccessful) {
                adapter.addItems(it.body()!!)
            }
        }, failure = {
        })
    }


}
