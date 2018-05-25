package com.indexer.ottohub

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.text.SpannableString
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.util.Log
import cn.nekocode.badge.BadgeDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.indexer.ottohub.adapter.GithubUserAdapter
import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.view.sp2px
import com.suthaw.restaurnat.adapter.SpacesItemDecoration
import com.suthaw.restaurnat.base.BaseViewHolder
import com.suthaw.restaurnat.rest.Config
import com.suthaw.restaurnat.rest.RestClient
import com.suthaw.restaurnat.rest.enqueue
import kotlinx.android.synthetic.main.activity_detail.*


class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        var username = intent.getStringExtra(Config.user_name)

        user_website.movementMethod = LinkMovementMethod.getInstance()


        val singleUser = RestClient.getService().getSingleGithubUser(username)
        singleUser.enqueue(success = {
            Glide.with(this).load(it.body()?.avatar_url)
                    .apply(RequestOptions.circleCropTransform()).into(avatar_image)
            if(it.body()?.site_admin!!){
                val staffDrawable = BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .text1(" Staff ")
                        .textSize(sp2px(this, 14F))
                        .badgeColor(-0xcc9967)
                        .build()
                val spannableString = SpannableString(TextUtils.concat(
                        username+"\n",staffDrawable.toSpannable()))
                user_name.text = spannableString


            }
            user_city.text = it.body()?.location
            user_bio.text = it.body()?.bio
            user_title_name.text= it.body()?.name
            user_website.text = it.body()?.blog
        }, failure = {

        })



    }


}
