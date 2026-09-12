package org.aioncyclus.aiongraphos.domain.model.dignity

import kotlinx.serialization.Serializable

@Serializable
enum class DignitySystemType()
{
    TRADITIONAL;

    fun getDignitySystem(): DignitySystem
    {
        return when(this)
        {
            TRADITIONAL -> TraditionalDignities()
        }
    }
}

