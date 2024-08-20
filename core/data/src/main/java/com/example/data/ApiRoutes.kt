package com.example.data

object KdtApiRoutes {

    private const val BASE = "https://658a-178-66-78-189.ngrok-free.app/api"
    const val CHAT = "$BASE/chat"
    const val CHAT_ASK = "$CHAT/ask"
    const val PLACES = "$BASE/places"
    const val PLACES_GET = "$PLACES/get"
    const val PLACES_BUY = "$PLACES/buy"

}

object CardApiRoutes {

    private const val BASE = "https://cardj.akbars.ru/widgetgateway/api"
    const val NEWS = "$BASE/News"

}