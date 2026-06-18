package org.aioncyclus.aiongraphos.domain.model.planet

data class Condition(
    val planet: Planet,
    val score: Int
)
{
    fun add(other: Condition): Condition {
        require(planet == other.planet) {
            "Cannot add conditions for different planets: $planet and ${other.planet}"
        }
        return Condition(planet, score + other.score)
    }
}
