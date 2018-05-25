package com.indexer.ottohub.view


import android.content.Context
import android.text.SpannableString
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.indexer.ottohub.model.GithubUser
import com.suthaw.restaurnat.base.BaseViewHolder
import kotlinx.android.synthetic.main.user_item.view.*
import cn.nekocode.badge.BadgeDrawable



class GithubUserViewHolder(itemView: View, listener: OnItemClickListener?) :
        BaseViewHolder(itemView, listener) {
    fun onBind(githubUser: GithubUser) {
        Glide.with(itemView.context).load(githubUser.avatar_url)
                .apply(RequestOptions.circleCropTransform()).into(itemView.user_image)
        itemView.user_name.text = githubUser.login
        if(githubUser.site_admin){
            val staffDrawable = BadgeDrawable.Builder()
                    .type(BadgeDrawable.TYPE_ONLY_ONE_TEXT)
                    .text1(" Staff ")
                    .textSize(sp2px(itemView.context, 14F))
                    .badgeColor(-0xcc9967)
                    .build()
            val spannableString = SpannableString(staffDrawable.toSpannable())
            itemView.user_description.text = spannableString
        }
    }

    fun sp2px(context: Context, spValue: Float): Float {
        val fontScale = context.resources.displayMetrics.scaledDensity
        return (spValue * fontScale + 0.5f)
    }
}