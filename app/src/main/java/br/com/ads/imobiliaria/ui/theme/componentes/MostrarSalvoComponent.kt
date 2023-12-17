package br.com.ads.imobiliaria.ui.theme.componentes

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun MostrarSalvoComponent(text : String){
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp, start = 20.dp),
        textAlign = TextAlign.Left,
        text = text,
        color = MaterialTheme.colorScheme.onPrimary
    )
}
