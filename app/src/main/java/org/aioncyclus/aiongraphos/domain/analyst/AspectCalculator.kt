package org.aioncyclus.aiongraphos.domain.analyst

import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.aspect.AspectType
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData

class AspectCalculator{

    fun calculateAspects(planetaryData: List<PlanetData>): List<Aspect>
    {
        val aspects=mutableListOf<Aspect>()

        //planetaryData.indices
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
        var distance: Double=planetDataA.planetPosition.longitude-planetDataB.planetPosition.longitude

        if (distance<0)
        {
            distance *= -1
        }
        if(distance>180)
        {
            distance=360-distance
        }

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
}