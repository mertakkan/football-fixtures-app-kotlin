package com.example.finalproject

data class FixtureResponse(val response: List<Fixture>)

data class Fixture(
    val fixture: FixtureDetails,
    val teams: FixtureTeams
)

data class FixtureDetails(
    val id: Int,
    val date: String,
    val status: FixtureStatus
)

data class FixtureTeams(
    val home: TeamDetails,
    val away: TeamDetails
)

data class TeamDetails(
    val name: String,
    val logo: String
)

data class FixtureStatus(
    val short: String,
    val long: String
)
