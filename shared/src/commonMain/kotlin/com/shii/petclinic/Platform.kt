package com.shii.petclinic

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform
