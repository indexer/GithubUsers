package com.indexer.ottohub

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import com.indexer.ottohub.adapter.GithubUserAdapter
import com.indexer.ottohub.viewmodel.GithubListViewModel
import com.suthaw.restaurnat.adapter.SpacesItemDecoration
import com.suthaw.restaurnat.base.BaseViewHolder
import com.suthaw.restaurnat.rest.Config
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BaseViewHolder.OnItemClickListener {
    lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubListViewModel: GithubListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager


    override fun onItemClick(position: Int) {
        val name = githubUserAdapter.getItem(position).login
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(Config.user_name, name)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        githubUserAdapter = GithubUserAdapter(this)
        linearLayoutManager = LinearLayoutManager(this)
        setUpRecyclerView()
        setUpGithubViewModel()
        setUpRecyclerViewListener()

    }

    private fun setUpRecyclerViewListener() {
        val recyclerViewOnScrollListener = object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val lastVisibleItemPosition = linearLayoutManager.findLastVisibleItemPosition()
                val id = githubUserAdapter.getItem(lastVisibleItemPosition).id
                run {
                    setObserverForLastId(id)
                }
            }
        }
        github_list.addOnScrollListener(recyclerViewOnScrollListener)
    }

    private fun setUpRecyclerView() {
        github_list.let {
            it.layoutManager = linearLayoutManager
            it.adapter = githubUserAdapter
            it.addItemDecoration(SpacesItemDecoration(16))
        }
    }


    private fun setUpGithubViewModel() {
        githubListViewModel = ViewModelProviders.of(this)
                .get(GithubListViewModel::class.java)
        setUpObserveForList(0)
    }

    private fun setObserverForLastId(id: Int) {
        githubListViewModel.getLastIdOrNot(github_list.canScrollVertically(1))
                ?.observe(this@MainActivity, Observer {
                    if(it==false) {
                        setUpObserveForList(id)
                    }
                })
    }


    private fun setUpObserveForList(lastInt: Int) {
        githubListViewModel.getGithubUser(lastInt)?.observe(this@MainActivity, Observer {
            githubUserAdapter.addItems(items = it!!)
            githubUserAdapter.notifyDataSetChanged()
        })
    }


}
