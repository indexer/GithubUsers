package com.indexer.ottohub.model



data class GithubUser(
    val login: String,
    val id: Int,
    val avatar_url: String,
    val gravatar_id: String,
    val url: String,
    val name:String,
    val html_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val starred_url: String,
    val subscriptions_url: String,
    val organizations_url: String,
    val repos_url: String,
    val events_url: String,
    val received_events_url: String,
    val type: String,
    val site_admin: Boolean,
    val bio :String,
    val public_repos : Int,
    val public_gist :Int,
    val location :String,
    val blog :String

)