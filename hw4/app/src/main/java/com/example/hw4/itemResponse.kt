package com.example.hw4
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class itemResponse(
    val Item:itemContent
):Serializable
data class itemContent(
    val BestOfferEnabled:Boolean,
    val ItemID:String,
    val EndTime:String,
    val StartTime:String,
    val ViewItemURLForNaturalSearch:String,
    val ListingType:String,
    val Location:String,
    val PictureURL:List<String>,
    val PostalCode:String,
    val PrimaryCategoryID:String,
    val PrimaryCategoryName:String,
    val Quantity:Integer,
    val Seller:Seller,
    val BidCount:Integer,
    val ConvertedCurrentPrice:_Price,
    val CurrentPrice:Price,
    val ListingStatus:String,
    val QuantitySold:Integer,
    val ShipToLocations:List<String>,
    val Site:String,
    val TimeLeft:String,
    val Title:String,
    val ItemSpecifics:ItemSpecifics,
    val PrimaryCategoryIDPath:String,
    val Storefront:Storefront,
    val Country:String,
    val ReturnPolicy:ReturnPolicy,
    val AutoPay:Boolean,
    val PaymentAllowerSite:List<String>,
    val HandlingTime:Integer,
    val ConditionID:Integer,
    val ConditionDisplayName:String,
    val TopRatedListing:Boolean,
    val GlobalShipping:Boolean,
    val NewBestOffer:Boolean,
    val ConditionDescription:String,
):Serializable
data class ItemSpecifics(
    val NameValueList:List<NameValue>,
):Serializable
data class ReturnPolicy(
    val ReturnsAccepted:String,
    val Refund:String,
    val ReturnsWithin:String,
    val ShippingCostPaidBy:String,
):Serializable

data class Storefront(
    val StoreName:String,
    val StoreURL:String,
):Serializable
data class NameValue(
    val Name:String,
    val Value:List<String>,
):Serializable
data class Seller(
    val UserID:String,
    val FeedbackRatingStar:String,
    val FeedbackScore:Integer,
    val PositiveFeedbackPercent:Double,
    val TopRatedSeller:Boolean,
):Serializable




data class _Price(
     val CurrencyID: String,
     val Value: String
): Serializable
