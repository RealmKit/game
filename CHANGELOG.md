# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

___

## 2.0 - ?

> #### System: `Player`
>
> >

> #### System: `Guild`
>
> > A `Guild` is a group of `Player` that can interact with each other.
> > `Player` can create, join and leave a `Guild`.
> > `Guild` offers `Stat` bonus to all `Players` in it.
> - [ ] create a `Guild`
> - [ ] join a `Guild`
> - [ ] leave a `Guild`
> - [ ] [Stat](src/main/kotlin/dev/realmkit/game/domain/stat/document/Stat.kt) bonus

> #### System: `Trade`
>
> > A `Trade` is a transaction between two `Players`.
> - [ ] trade `Items` and/or [Resources](src/main/kotlin/dev/realmkit/game/domain/resource/document/Resource.kt)
> - [ ] `TradeLogs`

## 1.0 - WIP

> ### [System: StaticData](src/main/kotlin/dev/realmkit/game/domain/staticdata)
>
> > Loads data from properties files
> - [x] [StaticDataProperties](src/main/kotlin/dev/realmkit/game/domain/staticdata/property/StaticDataProperties.kt)

> ### [System: Player](src/main/kotlin/dev/realmkit/game/domain/player)
>
> > A `Player` will be able to have multiples `Ships` and equip one at a time when at the `Hangar`.
> - [x] create a new [Player](src/main/kotlin/dev/realmkit/game/domain/player/document/Player.kt)
> - [x] gain `.experience` after defeating a [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt)
> - [x] `.levelUp()` when reaching a certain amount of `.experience`
> - [ ] level up [Stat](src/main/kotlin/dev/realmkit/game/domain/stat/document/Stat.kt) progression logic
> - [ ] equip `Ship`

> #### System: `Ship`
>
> > A `Ship` is a `Target` that can be equipped by the `Player`, it acts as the "character" of the `Player`.
> - [x] create an initial `Ship` when creating a [Player](src/main/kotlin/dev/realmkit/game/domain/player/document/Player.kt)
> - [ ] upgrade `Ship`

> #### System: `Upgrades`
>
> > `Upgrades` are used to improve the `Ship` stats.
> > `Ship` may have multiple `Upgrades` at the same time.
> - [ ] `Upgrades` table
> - [ ] `.equip()` `Upgrade`
> - [ ] `.unequip()` `Upgrade`

> #### System: `Hangar`
>
> > A `Hangar` is a place where the `Player` can equip a `Ship` and `Travel` to other places.
> - [ ] store `Ships`
> - [ ] upgrade `Ships`

> #### System: `Enemy Spawn`
>
> > `Enemy` random encounter generator based on `Location` and/or `Level`.
> > Could be used to generate `Enemy` for `Battle`, during `Travel`,
> > for `Quest`, `Dungeon`, `Boss` or `Word Boss`.
> - [ ] `Encounters`
> - [x] [Enemy](src/main/kotlin/dev/realmkit/game/domain/enemy/document/Enemy.kt)
> - [ ] `Enemy` generation

> #### [System: Target](src/main/kotlin/dev/realmkit/game/domain/target)
>
> > A `Target` is an entity that can attack or be attacked by another `Target`.
> - [x] [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt)
> - [x] [Stat](src/main/kotlin/dev/realmkit/game/domain/stat/document/Stat.kt)
> - [x] `.attack()`
> - [ ] `Loot` is the `Cargo` (`Items` and/or [Resource](src/main/kotlin/dev/realmkit/game/domain/resource/document/Resource.kt)) dropped by a [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt) after being
    defeated.
> - [ ] `.aggro` to be a weighted value that determines the chance of a [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt) to be attacked by
    another [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt).

> #### System: `Travel`
>
> > A `Travel` is a movement from one `Location` to another.
> > `Player` can `Travel` to other `Location` from the `Hangar`.
> > `TravelTime` is based on the distance between the two `Location`.
> - [ ] [Player](src/main/kotlin/dev/realmkit/game/domain/player/document/Player.kt) to explore `Locations`
> - [ ] `Locations` to have [Enemy](src/main/kotlin/dev/realmkit/game/domain/enemy/document/Enemy.kt) spawn

> #### [System: Battle](src/main/kotlin/dev/realmkit/game/domain/base)
>
> > A `Battle` is a fight between two `Targets`.
> > It will be an idle turn-based system where no input will be required after started.
> - [x] idle Turn-Based Battle between [Target](src/main/kotlin/dev/realmkit/game/domain/target/document/Target.kt)
> - [x] [BattleContextResult](src/main/kotlin/dev/realmkit/game/domain/battle/context/BattleContextResult.kt)
> - [ ] persist the battle results

> #### Infra: `Kafka Messaging`
>
> > This Service will not expose API endpoints, all communications will be done through Kafka.
> - [ ] setup Kafka consumer
> - [ ] `PlayerCreateRequest`
> - [ ] `BattleStartRequest`
> - [ ] setup Kafka producer
> - [ ] `PlayerCreated`
> - [ ] `BattleFinished`

### [Dependencies](gradle/libs.versions.toml)

- Working with 💜 `Kotlin` latest version
- Added `Kotlinx` for dealing with concurrency
- Added `Detek` plugin for Code Quality
- Added `Spotless` plugin for Code Quality
- Added `Sonarqube` plugin for Code Coverage
- Added `Dokka` plugin for generating KDoc documentation
- Added `Kotest` library for the best Kotlin test framework
- Added `Konform` library for data validation
