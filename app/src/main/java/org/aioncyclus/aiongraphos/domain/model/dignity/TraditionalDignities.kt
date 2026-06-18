package org.aioncyclus.aiongraphos.domain.model.dignity

import org.aioncyclus.aiongraphos.domain.analyst.AspectCalculator
import org.aioncyclus.aiongraphos.domain.analyst.LuminaryCalculator
import org.aioncyclus.aiongraphos.domain.calculator.HousesCalculator
import org.aioncyclus.aiongraphos.domain.calculator.ZodiacMapper
import org.aioncyclus.aiongraphos.domain.model.aspect.Aspect
import org.aioncyclus.aiongraphos.domain.model.chart.AstroChart
import org.aioncyclus.aiongraphos.domain.model.chart.Sect
import org.aioncyclus.aiongraphos.domain.model.house.HousesData
import org.aioncyclus.aiongraphos.domain.model.aspect.AspectType
import org.aioncyclus.aiongraphos.domain.model.planet.Condition
import org.aioncyclus.aiongraphos.domain.model.planet.Planet
import org.aioncyclus.aiongraphos.domain.model.planet.PlanetData
import org.aioncyclus.aiongraphos.domain.model.zodiac.Element
import org.aioncyclus.aiongraphos.domain.model.zodiac.FixedStar
import org.aioncyclus.aiongraphos.domain.model.zodiac.Sign
import org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships.EssentialRulerships
import org.aioncyclus.aiongraphos.domain.model.zodiac.rulerships.TraditionalRulerships
import org.aioncyclus.aiongraphos.domain.model.zodiac.term.PtolemaicTerms
import org.aioncyclus.aiongraphos.domain.model.zodiac.triplicity.DorotheanTriplicity

//TODO:
// - introduce granularity to the returned condition score
class TraditionalDignities: DignitySystem {

    override fun evaluateCondition(
        planet: Planet,
        astroChart: AstroChart,
        sect: Sect,
        aspects: List<Aspect>
    ): Condition {
        val planetData = astroChart.getPlanetData(planet)
        val sunData = astroChart.getPlanetData(Planet.SUN)
        val housesData= astroChart.housesData
        val essentialCondition = calculateEssentialCondition(planetData,sect)
        val accidentalCondition=calculateAccidentalCondition(planetData,sunData,housesData,aspects)
        val totalConditon=essentialCondition.add(accidentalCondition)
        return totalConditon
    }

    fun essentialRulershipsOf(
        planet: Planet
    ): EssentialRulerships {
        return TraditionalRulerships.of(planet)
    }

    fun triplicityRulerOf(element: Element, sect: Sect): Planet {
        return DorotheanTriplicity.rulerOf(element,sect)
    }

    fun termRulerOf(sign: Sign, degree: Int): Planet?
    {
        return PtolemaicTerms.rulerOf(sign,degree)
    }

    private fun isInTriplicity(planetData: PlanetData, sect: Sect): Boolean
    {
        val signElement = planetData.zodiacPosition.sign.element
        return planetData.planet == triplicityRulerOf(signElement,sect)
    }
    private fun isInTerm(planetData: PlanetData): Boolean
    {
        return planetData.planet == termRulerOf(planetData.zodiacPosition.sign,planetData.zodiacPosition.degreeInSign)
    }

    fun isInDomicile(planetData: PlanetData): Boolean
    {
        return essentialRulershipsOf(planetData.planet).domiciles.contains(planetData.zodiacPosition.sign)
    }

    fun isInExaltation(planetData: PlanetData): Boolean
    {
        return essentialRulershipsOf(planetData.planet).exaltation==planetData.zodiacPosition.sign
    }

    fun isInDecan(planetData: PlanetData): Boolean
    {
        return planetData.zodiacPosition.decan.ruler == planetData.planet
    }

    fun isInExile(planetData: PlanetData): Boolean
    {
        return exileOf(planetData.planet).contains(planetData.zodiacPosition.sign)
    }

    fun isInFall(planetData: PlanetData): Boolean
    {
        return fallOf(planetData.planet)==planetData.zodiacPosition.sign
    }


    fun fallOf(planet: Planet): Sign?= essentialRulershipsOf(planet).exaltation?.opposite()
    fun exileOf(planet: Planet): List<Sign> = essentialRulershipsOf(planet).domiciles.map { it.opposite() }

    //TODO:
    // - mutual reception
    fun calculateEssentialCondition(planetData: PlanetData, sect: Sect): Condition
    {
        var conditionScore = 0
        var hasEssentialDignity = false

        if(isInDomicile(planetData))
        {
            conditionScore += 5
            hasEssentialDignity = true
        }
        else if(isInExaltation(planetData))
        {
            conditionScore += 4
            hasEssentialDignity = true
        }
        else if(isInExile(planetData))
        {
            conditionScore += -5
            hasEssentialDignity = true
        }
        else if(isInFall(planetData))
        {
            conditionScore += -4
            hasEssentialDignity = true
        }

        if(isInTriplicity(planetData,sect))
        {
            conditionScore += 3
            hasEssentialDignity = true
        }
        if(isInTerm(planetData))
        {
            conditionScore += 2
            hasEssentialDignity = true
        }
        if(isInDecan(planetData))
        {
            conditionScore += 1
            hasEssentialDignity = true
        }

        if(hasEssentialDignity != true)
        {
            conditionScore += -5
        }

        return Condition(planetData.planet,conditionScore)
    }

    //TODO:
    // - besiegement(need applyingTo and separatingFrom functions)
    // - test if scoring system is ok

    fun isConjunctToStar(
        planetData: PlanetData,
        star: FixedStar,
        orb: Double = 1.0
    ): Boolean
    {
        return planetData.planetPosition.longitude>=star.longitude-orb
            && planetData.planetPosition.longitude<=star.longitude+orb
    }

    fun isOriental(
        planetData: PlanetData,
        sunData: PlanetData
    ): Boolean {

        val distance =
            ZodiacMapper.normaliseLongitude(
                planetData.planetPosition.longitude
                        - sunData.planetPosition.longitude)

        return distance < 180.0
    }

    fun isOccidental(
        planetData: PlanetData,
        sunData: PlanetData
    ): Boolean {
        return !isOriental(planetData, sunData)
    }

    fun calculateAccidentalCondition(planetData: PlanetData,
                                 sunData: PlanetData,
                                 housesData: HousesData,
                                 aspects: List<Aspect>): Condition
    {
        var conditionScore = 0

        conditionScore+= checkHouseScore(housesData,planetData)
        conditionScore+= checkRetrogradationScore(planetData)
        conditionScore+= checkMotionSpeed(planetData)
        if(planetData.planet== Planet.MOON)
        {
            conditionScore+=checkLunarPhase(planetData,sunData)
        }
        if(planetData.planet!=Planet.SUN)
        {
            conditionScore+=checkCombustStatus(planetData,sunData)
        }
        conditionScore+=checkSolarPhase(planetData,sunData)
        conditionScore+=checkNodeConjunction(planetData.planet,aspects)
        conditionScore+=checkBeneficConjunction(planetData.planet,aspects)
        conditionScore+=checkBeneficTrine(planetData.planet,aspects)
        conditionScore+=checkBeneficSextile(planetData.planet,aspects)
        conditionScore+=checkMaleficcConjunction(planetData.planet,aspects)
        conditionScore+=checkMaleficOpposition(planetData.planet,aspects)
        conditionScore+=checkMaleficSquare(planetData.planet,aspects)

        when
        {
            isConjunctToStar(planetData, FixedStar.REGULUS)-> conditionScore += 6
            isConjunctToStar(planetData, FixedStar.SPICA)-> conditionScore += 5
            isConjunctToStar(planetData, FixedStar.ALGOL)-> conditionScore+= -4
        }

        return Condition(planetData.planet,conditionScore)
    }

    fun checkHouseScore(houses: HousesData, planetData: PlanetData): Int
    {
        val planetLongitude= planetData.planetPosition.longitude
        val currentHouse= HousesCalculator.locateHouseOf(planetLongitude,houses)

        return when (currentHouse.number) {
            10,1 -> 5
            7,4,11 -> 4
            2,5 -> 3
            9 -> 2
            3 -> 1
            12 -> -5
            8,6 -> -2
            else -> 0
        }
    }

    fun checkRetrogradationScore(planetData: PlanetData): Int
    {
        if(planetData.planet == Planet.MOON || planetData.planet == Planet.SUN)
        {
            return 0
        }
        return when(planetData.isRetrograde)
        {
            false -> 4
            true -> -5
        }
    }


    fun checkMotionSpeed(planetData: PlanetData): Int {
        var planetSpeed=planetData.planetPosition.speedLongitude
        if (planetSpeed < 0) {
            planetSpeed *= -1
        }
        return when {
            planetSpeed > planetData.planet.meanSpeed -> 2
            planetSpeed < planetData.planet.meanSpeed  -> -2
            else -> 0
        }
    }

    fun checkLunarPhase(moonData: PlanetData,sunData: PlanetData):Int
    {
        return when
        {
            LuminaryCalculator.isWaxing(moonData,sunData)-> 2
            LuminaryCalculator.isWaning(moonData,sunData)-> -2
            else -> 0
        }
    }

    fun checkCombustStatus(
        planetData: PlanetData,
        sunData: PlanetData
    ): Int {

        val distance = AspectCalculator.angularDistance(
            planetData.planetPosition.longitude,
            sunData.planetPosition.longitude
        )

        return when {
            distance <= (17.0 / 60.0) -> 5      // Cazimi
            distance <= 8.5 -> -5               // Combust
            distance <= 17.0 -> -4              // Under beams
            else -> 5                           // Free from beams
        }
    }

    fun checkSolarPhase(planetData: PlanetData,sunData: PlanetData): Int
    {
        val planet=planetData.planet
        return when
        {
            (planet== Planet.VENUS || planet== Planet.MERCURY)
                    && isOccidental(planetData,sunData) -> 2
            (planet== Planet.VENUS || planet== Planet.MERCURY)
                    && isOriental(planetData,sunData) -> -2
            (planet== Planet.SATURN || planet== Planet.JUPITER || planet== Planet.MARS)
                    && isOriental(planetData,sunData) -> 2
            (planet== Planet.SATURN || planet== Planet.JUPITER || planet== Planet.MARS)
                    && isOccidental(planetData,sunData) -> -2
            else -> 0
        }
    }

    fun checkNodeConjunction(planet: Planet, aspects: List<Aspect>): Int
    {
        val northNodes=listOf<Planet>(Planet.TRUE_NORTH_NODE,Planet.MEAN_NORTH_NODE)
        val southNodes=listOf<Planet>(Planet.TRUE_SOUTH_NODE,Planet.MEAN_SOUTH_NODE)
        val northAspect= AspectCalculator.findAspectTo(planet,northNodes, AspectType.CONJUNCTION,aspects)
        val southAspect= AspectCalculator.findAspectTo(planet,southNodes, AspectType.CONJUNCTION,aspects)
        if(northAspect?.separation?.degree==0)
        {
            return 4
        }
        else if(southAspect?.separation?.degree==0)
        {
            return -4
        }
        return 0
    }

    fun checkBeneficConjunction(planet:Planet,aspects: List<Aspect>): Int
    {
        val benefics=listOf<Planet>(Planet.VENUS,Planet.JUPITER)
        val beneficAspect= AspectCalculator.findAspectTo(planet,benefics, AspectType.CONJUNCTION,aspects)

        if(beneficAspect?.separation?.degree==0)
        {
            return 5
        }
        return 0
    }

    fun checkBeneficTrine(planet:Planet,aspects: List<Aspect>): Int
    {
        val benefics=listOf<Planet>(Planet.VENUS,Planet.JUPITER)
        val beneficAspect= AspectCalculator.findAspectTo(planet,benefics, AspectType.TRINE,aspects)

        if(beneficAspect?.separation?.degree==0)
        {
            return 4
        }
        return 0
    }

    fun checkBeneficSextile(planet:Planet,aspects: List<Aspect>): Int
    {
        val benefics=listOf<Planet>(Planet.VENUS,Planet.JUPITER)
        val beneficAspect= AspectCalculator.findAspectTo(planet,benefics, AspectType.SEXTILE,aspects)

        if(beneficAspect?.separation?.degree==0)
        {
            return 3
        }
        return 0
    }

    fun checkMaleficcConjunction(planet:Planet,aspects: List<Aspect>): Int
    {
        val malefics=listOf<Planet>(Planet.MARS,Planet.SATURN)
        val maleficAspect= AspectCalculator.findAspectTo(planet,malefics, AspectType.CONJUNCTION,aspects)

        if(maleficAspect?.separation?.degree==0)
        {
            return -5
        }
        return 0
    }

    fun checkMaleficOpposition(planet: Planet,aspects: List<Aspect>):Int
    {
        val malefics=listOf<Planet>(Planet.MARS,Planet.SATURN)
        val maleficAspect= AspectCalculator.findAspectTo(planet,malefics, AspectType.OPPOSITION,aspects)

        if(maleficAspect?.separation?.degree==0)
        {
            return -4
        }
        return 0
    }

    fun checkMaleficSquare(planet: Planet,aspects: List<Aspect>):Int
    {
        val malefics=listOf<Planet>(Planet.MARS,Planet.SATURN)
        val maleficAspect= AspectCalculator.findAspectTo(planet,malefics, AspectType.SQUARE,aspects)

        if(maleficAspect?.separation?.degree==0)
        {
            return -4
        }
        return 0
    }


}