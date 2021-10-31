package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pokemon.data.domainmodel.Stat
import com.example.pokemon.data.domainmodel.StatInfo
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonStats(stats: List<StatInfo>?) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Base Stat",
            style = MaterialTheme.typography.h6.copy(
                color = MaterialTheme.colors.onSurface,
                fontWeight = FontWeight.Bold,
                fontSize = 26.sp
            )
        )
        Spacer(modifier = Modifier.requiredHeight(16.dp))
        StatList(stats)
    }
}

@Composable
fun StatList(stats: List<StatInfo>?) {
    stats?.map {
        StatInfo(it)
    }
}

@Composable
fun StatInfo(stat: StatInfo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 36.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = stat.stat.shortName)
        LinearProgressIndicator(
            progress = stat.baseStat / stat.stat.maxValue.toFloat(),
            backgroundColor = Color.LightGray,
            modifier = Modifier
                .height(22.dp)
                .clip(RoundedCornerShape(20.dp)),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PokemonStatsPreview() {
    val stats = listOf(
        StatInfo(
            stat = Stat.HP,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.ATTACK,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.SPECIAL_ATTACK,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.ACCURACY,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.DEFENSE,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.SPECIAL_DEFENSE,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.SPEED,
            baseStat = 123
        ),
        StatInfo(
            stat = Stat.EVASION,
            baseStat = 123
        ),
    )

    PokemonTheme {
        PokemonStats(stats = stats)
    }
}
