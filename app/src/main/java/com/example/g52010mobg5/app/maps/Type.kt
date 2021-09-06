package com.example.g52010mobg5.app.maps

enum class Type(val id: Int, val technicalName: String, val classicName: String) {
    DEFAULT(-2, "Default", "Default"),
    FAVORITE(-1, "Favorite", "Favorite"),
    TYPE1(1, "SAE J1772-2009", "Type 1"),
    TYPE2(25, "IEC 62196-2 Type2", "Type 2"),
    CHADEMO(2, "IEC 62196-3 Configuration AA", "CHAdeMO"),
    CSS(33, "IEC 62196-3 Configuration FF", "CSS"),
    TESLA(30, "Tesla (Model S/X)", "Tesla");

    override fun toString(): String {
        return classicName
    }

    companion object {
        fun parse(name: String): Type? {
            for (type in values()) {
                if (name.contains(type.classicName) || name.contains(type.technicalName)) {
                    return type
                }
            }
            return null
        }
    }
}