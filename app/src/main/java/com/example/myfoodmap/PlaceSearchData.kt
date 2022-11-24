package com.example.myfoodmap

data class PlaceSearchData(
    val photo: String, val placeName: String, val placeAddress: String, var bookmark: String,
    val search_x: String, val search_y: String)