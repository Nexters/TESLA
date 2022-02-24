package com.ozcoin.cookiepang.utils

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal
import java.math.BigInteger

class BigIntegerSerializer : KSerializer<BigInteger> {
    override val descriptor: SerialDescriptor = PrimitiveSerialDescriptor("BigInteger", PrimitiveKind.DOUBLE)
    override fun serialize(encoder: Encoder, value: BigInteger) {
        encoder.encodeDouble(value.toBigDecimal().toDouble())
    }

    override fun deserialize(decoder: Decoder): BigInteger {
        return BigDecimal(decoder.decodeDouble().toString()).toBigInteger()
    }
}
