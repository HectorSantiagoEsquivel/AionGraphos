<div align="center">
    <img width="200" height="200" alt="Aiongraphos" src="https://github.com/user-attachments/assets/aa46be0b-3df2-4452-afa6-6eb9c14d92cf" />
    <h1>Aiongraphos</h1>
    <p>A minimalist astrology app</p>
</div>

---


<p align="center">
  <img src="https://github.com/user-attachments/assets/9050a6e2-25da-4d60-bd38-5ca487f71466" width="30%" alt="Chart screen" />
  <img src="https://github.com/user-attachments/assets/f93d352d-424f-4415-a37f-a5d7b060dcf9" width="30%" alt="Planetary details screen" /> 
  <img src="https://github.com/user-attachments/assets/be49dfde-6783-4da9-891d-efb6f5b1db13" width="30%" alt="Settings screen" /> 
</p>

Aiongraphos is an Android application that calculates and displays current astrological transits without requiring a natal chart, an account, or an internet connection.

Aiongraphos is an **Astrological Weather App**: it shows you the actual positions and relationships of the planets for the current moment, and gives you individual planet evolution through the month.

Aiongraphos uses traditional astrological techniques, including planetary dignity and strength calculations based on the framework described by William Lilly, to help turn raw planetary data into a more readable picture of the sky.

## What can I do with it?

### High-precision calculations

Aiongraphos generates a chart for the current moment using **Swiss Ephemeris**, the high-precision astronomical calculation library developed by Astrodienst and used by Astro.com.

### Explore more than just the zodiac signs

Aiongraphos displays:

* Planetary positions and motion
* Multiple house systems, including Whole Sign, Equal Houses, Placidus, Regiomontanus and Koch
* Planetary aspects
* Traditional dignities
* Lots such as Fortune, Spirit, Eros, Basis, and Exaltation
* Lunar node information

The application supports both **traditional and modern planetary configurations**, allowing the amount of information shown to be adjusted to different approaches to astrology.

## Designed to feel like an instrument

Aiongraphos was designed as a simple tool for astrologers, beginner and advanced, who may want to consult the current state of the sky on the go. As such Aiongraphos deliberately avoids loaded visual language and noise.

No feeds. No endless cards. No notifications. No accounts. No personalised content. No horoscopes. The interface is minimalist and centred around the chart itself.

The visual design takes inspiration from scientific manuals, technical diagrams, astronomical instruments, early-2000s digital interfaces, and modern weather apps.

## Built to work offline

Aiongraphos follows an ethos of maximising the independence and autonomy of the user. Thus, all astronomical calculations are performed locally on the device, without requiring an external service or subscription.

Aiongraphos does **not** require a server to generate a chart, and its core chart calculation does not depend on an online astrology API.

As a result the application can continue to calculate charts without an internet connection.


# Release

[<img src="https://github.com/machiav3lli/oandbackupx/blob/034b226cea5c1b30eb4f6a6f313e4dadcbb0ece4/badge_github.png"
alt="Get it on GitHub"
height="80"
align="center">](https://github.com/HectorSantiagoEsquivel/AionGraphos/releases/latest)


## Engineering

Aiongraphos is a software engineering project designed and built from the ground up in Kotlin.

The application combines a graphical Android interface with a numerical astronomical calculation engine and a separate astrological domain layer.

### Technology

**Platform**

* Android
* Kotlin
* Java 17
* Minimum Android API 26

**UI**

* Jetpack Compose
* Material 3
* Custom Compose drawing and layout
* Light and dark themes

**Architecture**

* MVVM
* Hilt dependency injection
* Repository pattern
* Kotlin Coroutines 
* Kotlin Serialization

**Astronomical calculation**

* Swiss Ephemeris

## Architecture

The application separates the user interface, application data, astronomical calculations, and astrological logic.

A deliberate architectural constraint is that **chart settings are application configuration, not part of the astronomical domain model**.

For example, deciding whether the UI should display a particular planet is different from the underlying fact of that planet's calculated position. Keeping those concerns separate prevents presentation choices from leaking into the calculation layer.

## Astronomical calculations

Aiongraphos is powered by **Swiss Ephemeris** for planetary and astronomical calculations.

The ephemeris library is isolated behind an application-level provider rather than being accessed throughout the application directly. The rest of the project can therefore work with its own domain models instead of depending on the details of the underlying ephemeris implementation.

The current implementation supports:

* Sun through Pluto
* Lunar nodes
* House calculation
* Planetary positions
* Planetary speed and motion
* Configurable house systems
* Traditional and modern planet sets
* Configurable astrological lots

## Astrological logic

The calculation of a chart is only the first layer of the application.

A separate domain layer evaluates astrological relationships and conditions from the calculated chart.

As an example, the traditional dignity system considers factors such as essential dignity, accidental condition, sect, house placement, and aspects rather than treating a planet's zodiac sign as an isolated value.

This keeps the astrological rules independent from the Compose UI and makes it possible to expand the analytical side of the application without rebuilding the interface around it.

## Why I built it

Aiongraphos began as a personal project to explore whether a relatively small Android application could provide a complete, specialised tool while remaining **self-contained, offline-capable, and technically maintainable**.

Building it required working across several areas of software development rather than focusing on a single demonstration feature:

* Android application development
* Kotlin and Jetpack Compose
* Reactive UI state
* Dependency injection
* Persistence
* Numerical calculations
* Domain modelling
* Custom graphics
* Configuration management
* Separation of concerns

The result is intended to be a complete application rather than a collection of isolated programming exercises.


## Design principles

### Self-contained

The core experience should not depend on an external service being available.

### Separation of concerns

The astronomical engine, astrological rules, application configuration, persistence, and user interface have distinct responsibilities.

### Explicit domain models

The application prefers meaningful domain objects over passing raw calculation results directly into UI code.

### Configurable

Astrological preferences are treated as configuration rather than hard-coded presentation logic.

### Deliberate visual design

The interface is treated as part of the software, not merely a layer placed on top of it. Chart rendering, information hierarchy, interaction, and typography are designed together.

## Current status

**Aiongraphos V1 is in active development.**

The core chart calculation and presentation system is implemented, with the remaining work focused primarily on finalising settings, visual polish, and release preparation.

## Future possibilities

Potential future work includes:
* Configurable Asteroids
* Lilith calculations
* Additional Dignity and House Systems 
* Detailed Aspect Evolution
* Planetary Hours
* Saved User Locations.

## License

Aiongraphos is licensed under the **GNU Affero General Public License v3.0 (AGPL-3.0)**.

This project uses **Swiss Ephemeris**, developed by Astrodienst AG. Swiss Ephemeris is available under the GNU AGPL or the Swiss Ephemeris Professional License. Aiongraphos uses Swiss Ephemeris under the GNU AGPL.

See the [Swiss Ephemeris licensing documentation](https://www.astro.com/swisseph/) for details.
