package us.nineworlds.serenity.emby.server.model

import com.squareup.moshi.Json
import org.joda.time.LocalDateTime

data class QueryResult(@Json(name = "Items") val items: List<Item>,
    @Json(name = "TotalRecordCount") val totalRecordCount: Int)

data class Item(@Json(name = "Name") val name: String,
    @Json(name = "ServerId") val serverId: String,
    @Json(name = "Id") val id: String,
    @Json(name = "Etag") val etag: String?,
    @Json(name = "DateCreated") val dateCreated: LocalDateTime?,
    @Json(name = "CollectionType") val collectionType: String?,
    @Json(name = "Container") val container: String?,
    @Json(name = "PremiereDate") val premiereDateTime: LocalDateTime?,
    @Json(name = "Overview") val oveview: String?,
    @Json(name = "ProductionYear") val productionYear: String?,
    @Json(name = "OfficialRating") val officialRating: String?,
    @Json(name = "ParentId") val parentId: String?,
    @Json(name = "rating") val rating: Double?,
    @Json(name = "CommunityRating") val communityRating: Double?,
    @Json(name = "RunTimeTicks") val runTimeTicks: Long?,
    @Json(name = "UserData") val userData: UserData?,
    @Json(name = "MediaStreams") val mediaStreams: List<MediaStream>?,
    @Json(name = "MediaSources") val mediaSources: List<MediaSource>?,
    @Json(name = "EpisodeCount") val episodeCount: Long?)

data class UserData(@Json(name = "PlaybackPositionTicks") val playbackPositionTicks: Long?,
    @Json(name = "UnplayedItemCount") val unplayedItemCount: Long?,
    @Json(name = "PlayCount") val playCount: Long?,
    @Json(name = "IsFavorite") val isFavorite: Boolean?,
    @Json(name = "Played") val played: Boolean?,
    @Json(name = "Key") val key: String?,
    @Json(name = "PlaybackPercentage") val playbackPercentage: Double?)

data class MediaStream(@Json(name = "Codec") val codec: String?,
    @Json(name = "DisplayTitle") val displayTitle: String?,
    @Json(name = "AspectRatio") val aspectRatio: String?,
    @Json(name = "Type") val type: String?,
    @Json(name = "ChannelType") val channelType: String?,
    @Json(name = "Channels") val channels: String?)

data class MediaSource(@Json(name = "Id") val id: String?,
    @Json(name = "Container") val container: String?,
    @Json(name = "SupportsDirectStream") val directStream: Boolean?,
    @Json(name = "SupportsTranscoding") val transcoding: Boolean?)

data class UserItemData(@Json(name = "Rating") val rating: Double?,
    @Json(name = "PlayedPercentage") val playedPrecentage: Double?,
    @Json(name = "UnplayedItemCount") val unplayedItemCount: Int?,
    @Json(name = "PlaybackPositionTicks") val playbackPositionTicks: Long?,
    @Json(name = "PlayCount") val playCount: Int?,
    @Json(name = "IsFavorite") val isFavorite: Boolean?,
    @Json(name = "Likes") val likes: Boolean?,
    @Json(name = "LastPlayedDate") val lastPlayedDate: String?,
    @Json(name = "Played") val played: Boolean?,
    @Json(name = "Key") val key: String?,
    @Json(name = "ItemId") val itemId: String?)