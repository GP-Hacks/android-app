package com.example.data

object KdtApiRoutes {

    private const val BASE = "https://de0e-178-66-78-189.ngrok-free.app/api"

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

}

object CardApiRoutes {

    private const val BASE_FIRST = "https://cardj.akbars.ru/widgetgateway/api"

    const val NEWS = "$BASE_FIRST/News"


    private const val BASE_SECOND = "https://api.card-rt.ru"

    const val PARTNERS = "$BASE_SECOND/partner/default/get-partners"
    const val CATEGORIES = "$BASE_SECOND/category/category"

}