package com.indexer.ottohub.adapter

import android.view.ViewGroup
import com.suthaw.restaurnat.base.BaseAdapter


import android.view.LayoutInflater
import com.indexer.ottohub.R
import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.view.GithubUserViewHolder
import com.suthaw.restaurnat.base.BaseViewHolder


class GithubUserAdapter (var onItemClickListener: BaseViewHolder.OnItemClickListener):
        BaseAdapter<GithubUserViewHolder, GithubUser>() {

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        holder.onBind(mItems[position])
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.user_item, parent, false)
        return GithubUserViewHolder(view,onItemClickListener)
    }


}