package org.aioncyclus.aiongraphos.domain.model.aspect

/**
 * Represents a type of astrological aspect between two astrological objects.
 *
 * @property angle The exact geometric angle of the aspect in degrees.
 * @property defaultOrb The default allowable deviation in degrees from the exact angle within which the aspect is considered valid.
 */

enum class AspectType(
    val angle: Int,
    val defaultOrb: Int
) {
    CONJUNCTION(0, 8),
    SEXTILE(60, 4),
    SQUARE(90, 6),
    TRINE(120, 6),
    OPPOSITION(180, 8)
}