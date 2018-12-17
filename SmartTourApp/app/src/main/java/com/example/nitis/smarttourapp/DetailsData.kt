package com.example.nitis.smarttourapp

import java.io.Serializable

data class DetailsData(
        val meta: DMeta?,
        val response: DResponse?
) : Serializable

data class DResponse(
        val venue: DVenue?
)

data class DVenue(
        val allowMenuUrlEdit: Boolean?,
        val attributes: Attributes?,
        val beenHere: BeenHere?,
        val bestPhoto: BestPhoto?,
        val canonicalUrl: String?,
        val categories: List<DCategory?>?,
        val colors: Colors?,
        val contact: Contact?,
        val createdAt: Int?,
        val delivery: DDelivery?,
        val description: String?,
        val dislike: Boolean?,
        val hasMenu: Boolean?,
        val hereNow: HereNow?,
        val hierarchy: List<Hierarchy?>?,
        val hours: Hours?,
        val id: String?,
        val inbox: Inbox?,
        val likes: Likes?,
        val listed: Listed?,
        val location: DLocation?,
        val menu: Menu?,
        val name: String?,
        val ok: Boolean?,
        val page: Page?,
        val pageUpdates: PageUpdates?,
        val parent: Parent?,
        val photos: Photos?,
        val popular: Popular?,
        val price: Price?,
        val rating: Double?,
        val ratingColor: String?,
        val ratingSignals: Int?,
        val reasons: Reasons?,
        val shortUrl: String?,
        val specials: Specials?,
        val stats: Stats?,
        val storeId: String?,
        val timeZone: String?,
        val tips: Tips?,
        val url: String?,
        val verified: Boolean?
)

data class Popular(
        val isLocalHoliday: Boolean?,
        val isOpen: Boolean?,
        val richStatus: RichStatus?,
        val status: String?,
        val timeframes: List<Timeframe?>?
)

data class RichStatus(
        val entities: List<Any?>?,
        val text: String?
)

data class Timeframe(
        val days: String?,
        val includesToday: Boolean?,
        val `open`: List<Open?>?,
        val segments: List<Any?>?
)

data class Open(
        val renderedTime: String?
)

data class Menu(
        val anchor: String?,
        val label: String?,
        val mobileUrl: String?,
        val type: String?,
        val url: String?
)

data class Page(
        val pageInfo: PageInfo?,
        val user: DUser?
)


data class Photo(
        val prefix: String?,
        val suffix: String?
)

data class Lists(
        val groups: List<Group?>?
)

data class Tips(
        val count: Int?,
        val groups: List<Group?>?
)

data class PageInfo(
        val banner: String?,
        val description: String?,
        val links: Links?
)

data class Links(
        val count: Int?,
        val items: List<Item?>?
)

data class Stats(
        val tipCount: Int?
)

data class BeenHere(
        val count: Int?,
        val lastCheckinExpiredAt: Int?,
        val marked: Boolean?,
        val unconfirmedCount: Int?
)

data class Inbox(
        val count: Int?,
        val items: List<Any?>?
)

data class Specials(
        val count: Int?,
        val items: List<Any?>?
)

data class Contact(
        val facebook: String?,
        val facebookName: String?,
        val formattedPhone: String?,
        val phone: String?,
        val twitter: String?
)

data class HereNow(
        val count: Int?,
        val groups: List<Group?>?,
        val summary: String?
)

data class Group(
        val count: Int?,
        val items: List<Item?>?,
        val name: String?,
        val summary: String?,
        val type: String?
)

data class Parent(
        val categories: List<DCategory?>?,
        val id: String?,
        val location: DLocation?,
        val name: String?
)

data class DLocation(
        val address: String?,
        val cc: String?,
        val city: String?,
        val country: String?,
        val crossStreet: String?,
        val formattedAddress: List<String?>?,
        val labeledLatLngs: List<DLabeledLatLng?>?,
        val lat: Double?,
        val lng: Double?,
        val postalCode: String?,
        val state: String?
)

data class DLabeledLatLng(
        val label: String?,
        val lat: Double?,
        val lng: Double?
)

data class DCategory(
        val icon: DIcon?,
        val id: String?,
        val name: String?,
        val pluralName: String?,
        val primary: Boolean?,
        val shortName: String?
)

data class DIcon(
        val prefix: String?,
        val suffix: String?,
        val name: String?,
        val sizes: List<Int?>?
)

data class Hierarchy(
        val canonicalUrl: String?,
        val id: String?,
        val lang: String?,
        val name: String?
)

data class Likes(
        val count: Int?,
        val groups: List<Group?>?,
        val summary: String?
)

data class DDelivery(
        val id: String?,
        val provider: DProvider?,
        val url: String?
)

data class DProvider(
        val icon: DIcon?,
        val name: String?
)

data class PageUpdates(
        val count: Int?,
        val items: List<Any?>?
)

data class BestPhoto(
        val createdAt: Int?,
        val height: Int?,
        val id: String?,
        val prefix: String?,
        val source: Source?,
        val suffix: String?,
        val visibility: String?,
        val width: Int?
)

data class Source(
        val name: String?,
        val url: String?
)

data class Colors(
        val algoVersion: Int?,
        val highlightColor: HighlightColor?,
        val highlightTextColor: HighlightTextColor?
)

data class HighlightColor(
        val photoId: String?,
        val value: Int?
)

data class HighlightTextColor(
        val photoId: String?,
        val value: Int?
)


data class Todo(
        val count: Int?
)

data class DUser(
        val bio: String?,
        val contact: Contact?,
        val homeCity: String?,
        val lists: Lists?,
        val tips: Tips?,
        val lastName: String?,
        val firstName: String?,
        val gender: String?,
        val id: String?,
        val photo: Photo?,
        val type: String?
)

data class Listed(
        val count: Int?,
        val groups: List<Group?>?
)

data class Item(
        val agreeCount: Int?,
        val authorInteractionType: String?,
        val disagreeCount: Int?,
        val likes: Likes?,
        val text: String?,
        val todo: Todo?,
        val firstName: String?,
        val gender: String?,
        val lastName: String?,
        val canonicalUrl: String?,
        val collaborative: Boolean?,
        val description: String?,
        val editable: Boolean?,
        val entities: List<Entity?>?,
        val followers: Followers?,
        val listItems: ListItems?,
        val logView: Boolean?,
        val name: String?,
        val photo: Photo?,
        val `public`: Boolean?,
        val updatedAt: Int?,
        val url: String?,
        val createdAt: Int?,
        val id: String?,
        val displayName: String?,
        val displayValue: String?,
        val height: Int?,
        val prefix: String?,
        val source: Source?,
        val suffix: String?,
        val user: DUser?,
        val visibility: String?,
        val width: Int?,
        val reasonName: String?,
        val summary: String?,
        val type: String?
)

data class ListItems(
        val count: Int?,
        val items: List<Item?>?
)

data class Followers(
        val count: Int?
)

data class Entity(
        val indices: List<Int?>?,
        val `object`: Object?,
        val type: String?
)

data class Object(
        val url: String?
)

data class Hours(
        val dayData: List<Any?>?,
        val isLocalHoliday: Boolean?,
        val isOpen: Boolean?,
        val richStatus: RichStatus?,
        val status: String?,
        val timeframes: List<Timeframe?>?
)


data class Attributes(
        val groups: List<Group?>?
)


data class Photos(
        val count: Int?,
        val groups: List<Group?>?,
        val summary: String?,
        val createdAt: Int?,
        val height: Int?,
        val id: String?,
        val prefix: String?,
        val suffix: String?,
        val user: DUser?,
        val visibility: String?,
        val width: Int?
)


data class Price(
        val currency: String?,
        val message: String?,
        val tier: Int?
)

data class Reasons(
        val count: Int?,
        val items: List<Item?>?
)

data class DMeta(
        val code: Int?,
        val requestId: String?
)