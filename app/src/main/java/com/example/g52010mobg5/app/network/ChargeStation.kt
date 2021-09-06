package com.example.g52010mobg5.app.network

data class ChargeStation(
    val UsageType: UsageType?,
    val StatusType: StatusType?,
    val AddressInfo: AdressInfo,
    val Connections: List<Connections>,
    val NumberOfPoints: Int?
)


data class UsageType(
    val IsPayAtLocation: Boolean?,
    val isMembershipRequired: Boolean?,
    val Title: String?
)

data class StatusType(
    val IsOperational: Boolean?,
    val Title: String?
)

data class AdressInfo(
    val Title: String?,
    val AddressLine1: String?,
    val StateOrProvince: String?,
    val Town: String?,
    val Postcode: String?,
    val Latitude: Double,
    val Longitude: Double,
    val Distance: Double?
)

data class Connections(
    val ConnectionType: ConnectionType?,
    val PowerKW: Double?,
    val Amps: Int?,
    val Voltage: Int?,
)

data class ConnectionType(
    val ID: Int?,
    val FormalName: String?,
    val Title: String?
)
