package com.example.data

object KdtApiRoutes {

    private const val BASE = "http://95.174.92.20:8086/api"

    private const val CHAT = "$BASE/chat"
    const val CHAT_ASK = "$CHAT/ask"

    const val PLACES = "$BASE/places"
    const val PLACES_BUY = "$PLACES/buy"
    const val PLACES_CATEGORIES = "$PLACES/categories"

    private const val USER = "$BASE/user"
    const val USER_TOKEN = "$USER/token"

    const val CHARITY = "$BASE/charity"
    const val CHARITY_CATEGORIES = "$CHARITY/categories"
    const val CHARITY_DONATE = "$CHARITY/donate"

    const val VOTES = "$BASE/votes"
    const val VOTES_INFO = "$VOTES/info"
    const val VOTES_RATE = "$VOTES/rate"
    const val VOTES_PETITION = "$VOTES/petition"
    const val VOTES_CHOICE = "$VOTES/choice"
    const val VOTES_CATEGORIES = "$VOTES/categories"

}

object CardApiRoutes {

    private const val BASE_FIRST = "https://cardj.akbars.ru/widgetgateway/api"

    const val NEWS = "$BASE_FIRST/News"


    private const val BASE_SECOND = "https://api.card-rt.ru"

    const val PARTNERS = "$BASE_SECOND/partner/default/get-partners"
    const val CATEGORIES = "$BASE_SECOND/category/category"

}