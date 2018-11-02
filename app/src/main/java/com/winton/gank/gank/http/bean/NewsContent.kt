package com.winton.gank.gank.http.bean

/**
 * @author: winton
 * @time: 2018/11/2 3:01 PM
 * @desc: 描述
 */


data class NewsContent(
    val abstract: String,
    val action_extra: String,
    val action_list: List<Action>,
    val aggr_type: Int,
    val allow_download: Boolean,
    val article_sub_type: Int,
    val article_type: Int,
    val article_url: String,
    val ban_comment: Int,
    val ban_danmaku: Boolean,
    val behot_time: Int,
    val bury_count: Int,
    val cell_flag: Int,
    val cell_layout_style: Int,
    val cell_type: Int,
    val comment_count: Int,
    val content_decoration: String,
    val control_panel: ControlPanel,
    val cursor: Long,
    val danmaku_count: Int,
    val digg_count: Int,
    val display_url: String,
    val filter_words: List<FilterWord>,
    val forward_info: ForwardInfo,
    val group_flags: Int,
    val group_id: Long,
    val has_m3u8_video: Boolean,
    val has_mp4_video: Int,
    val has_video: Boolean,
    val hot: Int,
    val ignore_web_transform: Int,
    val interaction_data: String,
    val is_subject: Boolean,
    val item_id: Long,
    val item_version: Int,
    val large_image_list: List<LargeImage>,
    val level: Int,
    val log_pb: LogPb,
    val media_info: MediaInfo,
    val media_name: String,
    val middle_image: MiddleImage,
    val need_client_impr_recycle: Int,
    val publish_time: Int,
    val read_count: Int,
    val repin_count: Int,
    val rid: String,
    val share_count: Int,
    val share_info: ShareInfo,
    val share_type: Int,
    val share_url: String,
    val show_dislike: Boolean,
    val show_portrait: Boolean,
    val show_portrait_article: Boolean,
    val source: String,
    val source_icon_style: Int,
    val source_open_url: String,
    val tag: String,
    val tag_id: Long,
    val tip: Int,
    val title: String,
    val ugc_recommend: UgcRecommend,
    val url: String,
    val user_info: UserInfo,
    val user_repin: Int,
    val user_verified: Int,
    val verified_content: String,
    val video_detail_info: VideoDetailInfo,
    val video_duration: Int,
    val video_id: String,
    val video_style: Int
)

data class Action(
    val action: Int,
    val desc: String
)

data class UserInfo(
    val avatar_url: String,
    val description: String,
    val follow: Boolean,
    val follower_count: Int,
    val name: String,
    val schema: String,
    val user_auth_info: String,
    val user_id: Long,
    val user_verified: Boolean,
    val verified_content: String
)

data class FilterWord(
    val id: String,
    val is_selected: Boolean,
    val name: String
)

data class LargeImage(
    val height: Int,
    val uri: String,
    val url: String,
    val url_list: List<Url>,
    val width: Int
)

data class Url(
    val url: String
)

data class ControlPanel(
    val recommend_sponsor: RecommendSponsor
)

data class RecommendSponsor(
    val icon_url: String,
    val label: String,
    val night_icon_url: String,
    val target_url: String
)

data class ForwardInfo(
    val forward_count: Int
)

data class ShareInfo(
    val cover_image: Any,
    val description: Any,
    val on_suppress: Int,
    val share_type: ShareType,
    val share_url: String,
    val title: String,
    val token_type: Int,
    val weixin_cover_image: WeixinCoverImage
)

data class WeixinCoverImage(
    val height: Int,
    val uri: String,
    val url: String,
    val url_list: List<Url>,
    val width: Int
)

data class ShareType(
    val pyq: Int,
    val qq: Int,
    val qzone: Int,
    val wx: Int
)

data class UgcRecommend(
    val activity: String,
    val reason: String
)

data class MediaInfo(
    val avatar_url: String,
    val follow: Boolean,
    val is_star_user: Boolean,
    val media_id: Long,
    val name: String,
    val recommend_reason: String,
    val recommend_type: Int,
    val user_id: Long,
    val user_verified: Boolean,
    val verified_content: String
)

data class LogPb(
    val impr_id: String,
    val is_following: String
)

data class MiddleImage(
    val height: Int,
    val uri: String,
    val url: String,
    val url_list: List<Url>,
    val width: Int
)

data class VideoDetailInfo(
    val detail_video_large_image: DetailVideoLargeImage,
    val direct_play: Int,
    val group_flags: Int,
    val show_pgc_subscribe: Int,
    val video_id: String,
    val video_preloading_flag: Int,
    val video_type: Int,
    val video_watch_count: Int,
    val video_watching_count: Int
)

data class DetailVideoLargeImage(
    val height: Int,
    val uri: String,
    val url: String,
    val url_list: List<Url>,
    val width: Int
)