package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.aspect.AspectType
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
//TODO:
// - This class could be an object
class AspectCalculator{

    fun calculateAspects(planetaryData: List<PlanetData>): List<Aspect>
    {
        val aspects=mutableListOf<Aspect>()

        for(i in planetaryData.indices)
        {
            for(j in i+1 until planetaryData.size)
            {
                val aspect=checkAspectBetween(planetaryData[i], planetaryData[j])
                if(aspect !=null)
                {
                    aspects.add(aspect)
                }
            }
        }
        return aspects
    }

    private fun checkAspectBetween(planetDataA: PlanetData,planetDataB: PlanetData): Aspect?
    {
        val distance=angularDistance(planetDataA.planetPosition.longitude,planetDataB.planetPosition.longitude)

        for(aspectType in AspectType.entries)
        {

            var aspectOrb=distance-aspectType.angle
            if(aspectOrb<0)
            {
                aspectOrb *= -1
            }
            if(aspectOrb<= aspectType.defaultOrb)
            {
                return Aspect(
                    planetDataA.planet,
                    planetDataB.planet,
                    aspectType,
                    ZodiacMapper.longitudeToDegrees(aspectOrb)
                )
            }
        }
        return null
    }

    companion object {
        fun angularDistance(a: Double, b: Double): Double {
            var distance = a - b

            if (distance < 0) {
                distance *= -1
            }
            if (distance > 180) {
                distance = 360 - distance
            }

            if (distance > 180.0) {
                distance = 360.0 - distance
            }

            return distance
        }

        fun aspectBetween(astroPointA: Planet,astroPointB: Planet, aspects:List<Aspect>)
        : Aspect?
        {
            for(aspect in aspects)
            {
                if((aspect.planetA==astroPointA && aspect.planetB==astroPointB)
                    ||
                    (aspect.planetB == astroPointA && aspect.planetA==astroPointB))
                {
                    return aspect
                }
            }
            return null
        }

        fun findAspectTo(
            planet: Planet,
            targets: List<Planet>,
            aspectType: AspectType,
            aspects: List<Aspect>
        ): Aspect? {

            for (target in targets) {

                val aspect = aspectBetween(
                        planet,
                        target,
                        aspects
                    )

                if (aspect?.aspectType == aspectType) {
                    return aspect
                }
            }

            return null
        }
    }
}