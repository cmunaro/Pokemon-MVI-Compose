package com.example.pokemon.data.domainmodel

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.pokemon.data.model.PokemonResponse
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "pokemon")
data class Pokemon(
    @PrimaryKey var id: Int = 0,
    var name: String = "",
    var height: Int = 0,
    var weight: Int = 0,
    var stats: List<StatInfo> = emptyList(),
    var types: List<Type> = emptyList()
) : Parcelable

@Parcelize
data class StatInfo(
    @PrimaryKey val stat: Stat,
    val baseStat: Int,
) : Parcelable

@Parcelize
enum class Stat : Parcelable {
    HP,
    ATTACK,
    DEFENSE,
    SPECIAL_ATTACK,
    SPECIAL_DEFENSE,
    SPEED,
    ACCURACY,
    EVASION,
    UNKNOWN
}

@Parcelize
enum class Type : Parcelable {
    NORMAL,
    FIGHTING,
    FLYING,
    POISON,
    GROUND,
    ROCK,
    BUG,
    GHOST,
    STEEL,
    FIRE,
    WATER,
    GRASS,
    ELECTRIC,
    PSYCHIC,
    ICE,
    DRAGON,
    DARK,
    FAIRYm,
    UNKNOWN,
    SHADOW
}

fun PokemonResponse.toPokemon(): Pokemon {
    return Pokemon(
        id = this.id,
        name = this.name,
        height = this.height,
        weight = this.weight,
        stats = this.stats.map { statResponse ->
            StatInfo(
                stat = Stat.values().firstOrNull {
                    it.name.lowercase().replace("_", "") == statResponse.stat.name.lowercase().replace("-", "")
                } ?: Stat.UNKNOWN,
                baseStat = statResponse.baseStat
            )
        },
        types = this.types.map { responseType ->
            Type.values()
                .firstOrNull { it.name.lowercase() == responseType.type.name.lowercase() }
                ?: Type.UNKNOWN
        }
    )
}