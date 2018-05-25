package com.indexer.ottohub.view


import android.text.SpannableString
import android.text.TextUtils
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.indexer.ottohub.model.GithubUser
import com.indexer.ottohub.base.BaseViewHolder
import kotlinx.android.synthetic.main.user_item.view.*
import cn.nekocode.badge.BadgeDrawable


class GithubUserViewHolder(itemView: View, listener: OnItemClickListener?) :
        BaseViewHolder(itemView, listener) {
    fun onBind(githubUser: GithubUser) {
        Glide.with(itemView.context).load(githubUser.avatar_url)
                .apply(RequestOptions.circleCropTransform()).into(itemView.user_image)
        when {
            githubUser.site_admin -> {
                val staffDrawable = BadgeDrawable.Builder()
                        .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                        .text1(" Staff ")
                        .textSize(sp2px(itemView.context, 14F))
                        .badgeColor(-0xcc9967)
                        .build()
                val spannableString = SpannableString(TextUtils.concat(
                        githubUser.login+"\n\n",staffDrawable.toSpannable()))
                itemView.user_name.text = spannableString
            }
            else -> itemView.user_name.text = githubUser.login
        }

    }


}