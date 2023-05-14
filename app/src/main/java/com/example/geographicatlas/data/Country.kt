package com.example.geographicatlas.data

data class Country(
    val area: Double,
    val capital: List<String>?,
    val capitalInfo: CapitalInfo,
    val cca2: String,
    val continents: List<String>,
    val currencies: Map<String, Currency>,
    val flags: Flags,
    val latlng: List<Double>,
    val maps: Maps,
    val name: Name,
    val population: Int,
    val region: String,
    val subregion: String,
    val timezones: List<String>,
    var unMember: Boolean
)