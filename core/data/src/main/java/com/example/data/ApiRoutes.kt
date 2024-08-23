package com.example.data

object KdtApiRoutes {

    private const val BASE = "http://95.174.92.20:8086/api"

    private const val CHAT = "$BASE/chat"
    const val CHAT_ASK = "$CHAT/ask"

    private const val PLACES = "$BASE/places"
    const val PLACES_GET = "$PLACES/get"
    const val PLACES_BUY = "$PLACES/buy"
    const val PLACES_CATEGORIES = "$PLACES/categories"

    private const val USER = "$BASE/user"
    const val USER_TOKEN = "$USER/token"

    private const val CHARITY = "$BASE/charity"
    const val CHARITY_GET = "$CHARITY/get"
    const val CHARITY_CATEGORIES = "$CHARITY/categories"
    const val CHARITY_DONATE = "$CHARITY/donate"

    const val VOTES = "$BASE/votes"
    const val VOTES_GET = "$VOTES/get"
    const val VOTES_RATE = "$VOTES/rate"
    const val VOTES_PETITION = "$VOTES/petition"
    const val VOTES_CHOICE = "$VOTES/choice"

}

object CardApiRoutes {

    private const val BASE_FIRST = "https://cardj.akbars.ru/widgetgateway/api"

    const val NEWS = "$BASE_FIRST/News"


    private const val BASE_SECOND = "https://api.card-rt.ru"

    const val PARTNERS = "$BASE_SECOND/partner/default/get-partners"
    const val CATEGORIES = "$BASE_SECOND/category/category"

}