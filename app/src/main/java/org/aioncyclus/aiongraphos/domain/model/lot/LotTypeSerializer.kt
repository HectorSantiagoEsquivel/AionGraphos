package org.aioncyclus.aiongraphos.domain.model.lot

import kotlinx.serialization.KSerializer

import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object LotTypeSerializer : KSerializer<LotType> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor(
            "LotType",
            PrimitiveKind.STRING
        )

    override fun serialize(
        encoder: Encoder,
        value: LotType
    ) {
        encoder.encodeString(value.id)
    }

    override fun deserialize(
        decoder: Decoder
    ): LotType {
        return when (val id = decoder.decodeString()) {
            "fortune" -> LotType.Fortune
            "spirit" -> LotType.Spirit
            "eros" -> LotType.Eros
            "exaltation" -> LotType.Exaltation
            "basis" -> LotType.Basis
            else -> throw SerializationException(
                "Unknown LotType: $id"
            )
        }
    }
}