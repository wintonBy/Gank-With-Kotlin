package com.winton.gank.gank.http.response

/**
 * @author: winton
 * @time: 2018/10/31 10:35 AM
 * @desc: 煎蛋API 响应
 */
data class JDListResponse(
    val status: String,
    val current_page: Int,
    val total_comments: Int,
    val page_count: Int,
    val count: Int,
    val comments: List<Comment>
)

data class Comment(
    val comment_ID: String,
    val comment_post_ID: String,
    val comment_author: String,
    val comment_date: String,
    val comment_date_gmt: String,
    val comment_content: String,
    val user_id: String,
    val vote_positive: String,
    val vote_negative: String,
    val sub_comment_count: String,
    val text_content: String,
    val pics: List<String>
)