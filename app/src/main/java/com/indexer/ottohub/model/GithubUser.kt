package com.indexer.ottohub.model


data class GithubUser(
        var login: String? = "-",
        var id: Int? = 0,
        var avatar_url: String,
        var gravatar_id: String? = "-",
        var url: String? = "-",
        var name: String? = "-",
        var html_url: String? = "-",
        var followers_url: String? = "-",
        var following_url: String? = "-",
        var gists_url: String? = "-",
        var starred_url: String? = "-",
        var subscriptions_url: String? = "-",
        var organizations_url: String? = "-",
        var repos_url: String? = "-",
        var events_url: String? = "-",
        var received_events_url: String? = "-",
        var type: String? = "-",
        var site_admin: Boolean,
        var bio: String? = "-",
        var public_repos: Int,
        var public_gist: Int,
        var location: String? = "-",
        var blog: String? = "-"

)