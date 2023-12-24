package com.example.hw4
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class SearchResponse(
    val findItemsAdvancedResponse: List<FindItemsAdvancedResponse>,
    @Transient val otherData: Any
): Serializable
data class FindItemsAdvancedResponse(
    val timestamp: List<String>,
    val ack: List<String>,
    val version: List<String>,
    val searchResult: List<SearchResult>,
    val paginationOutput: List<PaginationOutput>,
    val itemSearchURL: List<String>
): Serializable
data class SearchResult(
    @SerializedName("@count")val count: String,
    val item: List<Item>,
): Serializable
data class PaginationOutput(
    val pageNumber: List<String>,
    val entriesPerPage: List<String>,
    val totalPages: List<String>,
    val totalEntries: List<String>
): Serializable
data class Item(
    val itemId: List<String>,
    val title: List<String>,
    val globalId: List<String>,
    val primaryCategory: List<PrimaryCategory>,
    val galleryURL: List<String>,
    val viewItemURL: List<String>,
    val autoPay: List<String>,
    val postalCode: List<String>,
    val location: List<String>,
    val country: List<String>,
    val shippingInfo: List<ShippingInfo>,
    val sellingStatus: List<SellingStatus>,
    val listingInfo: List<ListingInfo>,
    val ReturnsAccepted: List<String>,
    val condition: List<Condition>,
    val isMultiVariationListing: List<String>,
    val discountPriceInfo: List<DiscountPriceInfo>,
    val topRatedListing: List<String>,
): Serializable
data class DiscountPriceInfo(
    val originalRetailPrice: List<Price>,
    val pricingTreatment: List<String>,
    val soldOnEbay: List<String>,
    val soldOffEbay: List<String>,
): Serializable
data class Condition(
    val conditionId: List<String>,
    val conditionDisplayName: List<String>,
): Serializable
data class ListingInfo(
    val bestOfferEnabled: List<String>,
    val buyItNowAvailable: List<String>,
    val startTime: List<String>,
    val endTime: List<String>,
    val listingType: List<String>,
    val gift: List<String>,
    val watchCount: List<String>,
):  Serializable
data class SellingStatus(
    val currentPrice: List<Price>,
    val convertedCurrentPrice: List<Price>,
    val sellingState: List<String>,
    val timeLeft: List<String>,
): Serializable
data class PrimaryCategory(
    val categoryId: List<String>,
    val categoryName: List<String>,
): Serializable
data class ShippingInfo(
    val shippingServiceCost: List<ShippingServiceCost>,
    val shippingType: List<String>,
    val shipToLocations: List<String>,
    val expeditedShipping: List<String>,
    val oneDayShippingAvailable: List<String>,
    val handlingTime: List<String>,
): Serializable
data class ShippingServiceCost(
    @SerializedName("@currencyId") val currencyId: String,
    val __value__: String,
): Serializable


// data class ReturnPolicy(
//     val ReturnsAccepted: String,
//     val Refund: String,
//     val ReturnsWithin: String,
//     val ShippingCostPaidBy: String,
//     val InternationalReturnsAccepted: String,
// )
// data class Storefront(
//     val storeName: String,
//     val storeURL: String
// )
// data class ItemSpecifics(
//     val NameValueList: List<NV>

// )
// data class NV(
//     val Name: String,
//     val Value: String
// )
data class Price(
    @SerializedName("@currencyId") val currencyId: String,
     val __value__: String
): Serializable
// data class Seller(
//     val UserID: String,
//     val FeedbackRatingStar: String,
//     val FeedbackScore: Integer,
//     val TopRatedSeller: Boolean,
//     val PositiveFeedbackPercent: Double
// )

