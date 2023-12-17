package br.com.ads.imobiliaria.ui.theme.componentes

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DivisorComponent (){
    Divider(
        modifier = Modifier.padding(horizontal = 8.dp)
    )
}