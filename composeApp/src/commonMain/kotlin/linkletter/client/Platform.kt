package linkletter.client

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform