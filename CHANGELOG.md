# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

___

## [1.0.0] - WIP

- [System: Player](src/main/kotlin/dev/realmkit/game/domain/player)
    - start a game
    - level up
- [System: Stat](src/main/kotlin/dev/realmkit/game/domain/stat)
    - attributes
    - rates
    - multipliers
    - progressions
- [System: StaticData](src/main/kotlin/dev/realmkit/game/domain/staticdata)
    - loads data from properties files
- [System: Battle](src/main/kotlin/dev/realmkit/game/domain/base)
    - starts a battle
    - turn based
    - collect battle result log
- [MongoDB Integration](src/main/kotlin/dev/realmkit/game/domain/base/extension/MongoRepositoryExtensions.kt)
- Data Validation

### [Dependencies](gradle/libs.versions.toml)

- Working with 💜 `Kotlin` latest version
- Added `Kotlinx` for dealing with concurrency
- Added `Detek` plugin for Code Quality
- Added `Spotless` plugin for Code Quality
- Added `Sonarqube` plugin for Code Coverage
- Added `Dokka` plugin for generating KDoc documentation
- Added `Kotest` library for the best Kotlin test framework
- Added `Konform` library for data validation
