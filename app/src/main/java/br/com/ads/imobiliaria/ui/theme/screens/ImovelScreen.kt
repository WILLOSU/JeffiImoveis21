package br.com.ads.imobiliaria.ui.theme.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.com.ads.imobiliaria.banco.dao.ImovelDAO
import br.com.ads.imobiliaria.model.Imovel
import br.com.ads.imobiliaria.ui.theme.componentes.ImagemCardComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoBoldComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TopBarComponent
import br.com.ads.imobiliaria.ui.theme.componentes.EditTextComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoComponent

@Composable
fun ImovelScreen(imovelDAO: ImovelDAO, navController: NavHostController) {
    Scaffold(
        topBar = {
            TopBarComponent("Imovel", navController)
        },
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary),
//                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var imoveis by remember { mutableStateOf(listOf<Imovel>()) }
            imoveis = imovelDAO.obterTodos()
            var selectedMatricula by remember{mutableStateOf("")}
            val listState = rememberLazyListState()
            //MOSTRAR LISTA DE IMOVEIS
            LazyColumn(state = listState,
                modifier = Modifier.heightIn(min = 250.dp, max = 250.dp)
            ) {
                items(imoveis) { imovel ->
                    BlocoImovel(imovel, selectedMatricula){ newSelectedMatricula ->
                        selectedMatricula = newSelectedMatricula
                    }
                }
            }
            var isInputEmpty by rememberSaveable { mutableStateOf(false) }
            val matricula = EditTextComponent("Matricula", isInputEmpty)
            val endereco = EditTextComponent("Endereço", isInputEmpty)
            val valorAluguel = EditTextComponent("Valor Aluguel", isInputEmpty)

            //INSERIR IMOVEL
            Button(
                onClick = {
                    if(endereco.isNotEmpty() && matricula.isNotEmpty()){
                        val newImovel = Imovel(matricula, endereco, valorAluguel.toFloat())
                        imovelDAO.inserir(newImovel)
                    }else{
                        isInputEmpty = true
                    }
                    //ATUALIZANDO LISTA
                    imoveis = imovelDAO.obterTodos()
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Inserir")
            }
            //DELETAR IMOVEL
            Button(
                onClick = {
                    if(selectedMatricula.isNotEmpty()){
                        imovelDAO.excluir(selectedMatricula)
                        imoveis = imovelDAO.obterTodos()
                    }
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Deletar")
            }
            //EDITAR IMOVEL
            Button(
                onClick = {
                    if(endereco.isNotEmpty() && valorAluguel.isNotEmpty()){
                        val newImovel = Imovel(selectedMatricula, endereco, valorAluguel.toFloat())
                        imovelDAO.atualizar(newImovel)
                    }else{
                        isInputEmpty = true
                    }
                    imoveis = imovelDAO.obterTodos()
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth()
            ) {
                TextoComponent("Editar")
            }
        }
    }
}

@Composable
fun BlocoImovel(imovel: Imovel, selectedCpf: String, onSelectedCpfChange: (String) -> Unit){
    val matriculaImovel = imovel.matricula
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .selectable(
                selected = matriculaImovel == selectedCpf,
                onClick = {
                    if (selectedCpf != matriculaImovel) {
                        onSelectedCpfChange(matriculaImovel)
                    } else {
                        onSelectedCpfChange("")
                    }
                }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(2.dp, color = Color.Blue),
        colors = CardDefaults.cardColors(
            containerColor = if (matriculaImovel == selectedCpf) {
                Color(0xFF8A05BE) // Cor roxa do Nubank
            } else {
                Color(0xFF55286F) // Cor primária do Nubank
            }
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagemCardComponent()
            Column {
                TextoBoldComponent("Matrícula Imovel: " + matriculaImovel)
                TextoBoldComponent("Endereço: " + imovel.endereco)
                TextoBoldComponent("Valor Aluguel: " + imovel.valoraluguel.toString())
            }
        }
    }

}
