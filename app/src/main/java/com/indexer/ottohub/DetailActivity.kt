package com.indexer.ottohub

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import cn.nekocode.badge.BadgeDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.view.sp2px
import com.indexer.ottohub.viewmodel.GithubDetailViewModel
import com.suthaw.restaurnat.rest.Config
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    private lateinit var githubDetailViewModel: GithubDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        toolbar.setNavigationIcon(R.drawable.menu_close)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val username = intent.getStringExtra(Config.user_name)
        user_website.movementMethod = LinkMovementMethod.getInstance()
        setUpandUiUpdate(username)


    }

    private fun setUpandUiUpdate(username:String){
        githubDetailViewModel = ViewModelProviders.of(this).get(GithubDetailViewModel::class.java)
        githubDetailViewModel.getGithubUser(username)?.observe(this, Observer {
            setUpDetailView(it)
        })
    }


    private fun setUpDetailView(githubUser: GithubUser?) {
        Glide.with(this).load(githubUser?.avatar_url)
                .apply(RequestOptions.circleCropTransform()).into(avatar_image)
        if (githubUser?.site_admin!!) {
            val staffDrawable = BadgeDrawable.Builder()
                    .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                    .text1(" Staff ")
                    .textSize(sp2px(this, 14F))
                    .badgeColor(-0xcc9967)
                    .build()
            val spannableString = SpannableString(TextUtils.concat(
                    githubUser.login + "\n", staffDrawable.toSpannable()))
            user_name.text = spannableString
        } else {
            user_name.text = githubUser.login
        }
        user_city.text = githubUser.location
        user_bio.text = githubUser.bio
        user_title_name.text = githubUser.name
        user_website.text = githubUser.blog
    }


}
