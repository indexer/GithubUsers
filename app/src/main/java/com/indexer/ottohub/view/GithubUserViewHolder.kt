package com.indexer.ottohub.view


import android.view.View
import com.indexer.ottohub.model.GithubUser
import com.suthaw.restaurnat.base.BaseViewHolder
import kotlinx.android.synthetic.main.user_item.view.*


class GithubUserViewHolder(itemView: View, listener: OnItemClickListener?) : BaseViewHolder(itemView, listener) {

    fun onBind(githubUser: GithubUser, position: Int) {
        itemView.user_name.text = githubUser.login
    }
}