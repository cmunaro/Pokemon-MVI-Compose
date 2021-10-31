package com.example.pokemon.ui.screen.pokemondetail.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.constraintlayout.compose.ChainStyle
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.pokemon.data.domainmodel.Type
import com.example.pokemon.ui.theme.PokemonTheme

@Composable
fun PokemonTypeList(types: List<Type>) {
    ConstraintLayout(
        modifier = Modifier.fillMaxWidth(),
    ) {
        val refs = ArrayList<ConstrainedLayoutReference>()
        types.map { type ->
            val ref: ConstrainedLayoutReference = createRef()
            refs.add(ref)
            PokemonTypeChip(
                type = type,
                modifier = Modifier.constrainAs(ref) {}
            )
        }
        createHorizontalChain(chainStyle = ChainStyle.Spread, elements = refs.toTypedArray())
    }
}

@Preview
@Composable
fun PokemonTypeListPreview() {
    PokemonTheme {
        PokemonTypeList(types = listOf(Type.DRAGON, Type.FIRE, Type.ELECTRIC))
    }
}