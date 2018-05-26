package com.indexer.ottohub

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.indexer.ottohub.adapter.GithubUserAdapter
import com.indexer.ottohub.viewmodel.GithubListViewModel
import com.indexer.ottohub.adapter.SpacesItemDecoration
import com.indexer.ottohub.base.BaseViewHolder
import com.indexer.ottohub.listener.ConnectivityReceiver
import com.indexer.ottohub.rest.Config
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), BaseViewHolder.OnItemClickListener,ConnectivityReceiver.ConnectivityReceiverListener {
    lateinit var githubUserAdapter: GithubUserAdapter
    private lateinit var githubListViewModel: GithubListViewModel
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var mSnackBar: Snackbar? = null


    override fun onItemClick(position: Int) {
        val name = githubUserAdapter.getItem(position).login
        val intent = Intent(this@MainActivity, DetailActivity::class.java)
        intent.putExtra(Config.user_name, name)
        startActivity(intent)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar_main)
        supportActionBar?.setDisplayShowTitleEnabled(true)
        githubUserAdapter = GithubUserAdapter(this)
        linearLayoutManager = LinearLayoutManager(this)
        registerReceiver(ConnectivityReceiver(),
                IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION))

    }

    override fun onResume() {
        super.onResume()
        ConnectivityReceiver.connectivityReceiverListener = this

    }


    private fun showMessage(isConnected: Boolean) {
        if (!isConnected) {
            val messageToUser = "You are offline now."
            loading.visibility = View.GONE
            mSnackBar = Snackbar.make(findViewById(R.id.relativeLayout), messageToUser, Snackbar.LENGTH_LONG)
            mSnackBar?.duration = Snackbar.LENGTH_INDEFINITE
            mSnackBar?.show()
        } else {
            setUpRecyclerView()
            setUpGithubViewModel()
            setUpRecyclerViewListener()
            mSnackBar?.dismiss()
        }


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

    private fun setObserverForLastId(id: Int?) {
        githubListViewModel.getLastIdOrNot(github_list.canScrollVertically(1))
                ?.observe(this@MainActivity, Observer {
                    if(it==false) {
                        setUpObserveForList(id)
                    }
                })
    }


    private fun setUpObserveForList(lastInt: Int?) {
        githubListViewModel.getGithubUser(lastInt)?.observe(this@MainActivity, Observer {
            loading.visibility = View.GONE
            github_list.visibility = View.VISIBLE
            githubUserAdapter.addItems(items = it!!)
            githubUserAdapter.notifyDataSetChanged()
        })
    }

    override fun onNetworkConnectionChanged(isConnected: Boolean) {
        showMessage(isConnected)
    }


}
