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
import br.com.ads.imobiliaria.banco.dao.InquilinoDAO
import br.com.ads.imobiliaria.model.Inquilino
import br.com.ads.imobiliaria.ui.theme.componentes.ImagemCardComponent
import br.com.ads.imobiliaria.ui.theme.componentes.MenuImoveisComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoBoldComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TopBarComponent
import br.com.ads.imobiliaria.ui.theme.componentes.EditTextComponent
import br.com.ads.imobiliaria.ui.theme.componentes.TextoComponent

@Composable
fun InquilinoScreen(
    inquilinoDAO: InquilinoDAO,
    navController: NavHostController,
    imovelDAO: ImovelDAO
) {
    Scaffold(
        topBar = {
            TopBarComponent("Inquilino", navController)
        }
    ) { innerPadding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.secondary),
//                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var inquilinos by remember { mutableStateOf(listOf<Inquilino>()) }
            inquilinos = inquilinoDAO.obterTodos()
            var selectedCpf by remember{mutableStateOf("")}
            var selectedMatricula by remember{mutableStateOf("")}
            val listState = rememberLazyListState()
            //MOSTRAR LISTA
            LazyColumn(state = listState,
                modifier = Modifier.heightIn(min = 250.dp, max = 250.dp)
            ) {
                items(inquilinos) { inquilino ->
                    BlocoInquilino(inquilino, selectedCpf){ newSelectedCpf ->
                        selectedCpf = newSelectedCpf
                    }
                }
            }
            var isInputEmpty by rememberSaveable { mutableStateOf(false) }
            val cpf = EditTextComponent("CPF", isInputEmpty)
            val nome = EditTextComponent("Nome", isInputEmpty)
            val valorCalcao = EditTextComponent("Valor Calção", isInputEmpty)

            val imoveis = imovelDAO.obterTodos()
            MenuImoveisComponent(imoveis){ newSelectedImovel ->
                selectedMatricula = newSelectedImovel
            }

            Button(
                onClick = {
                    if(nome.isNotEmpty() && cpf.isNotEmpty()){
                        val newInquilino = Inquilino(cpf, nome, valorCalcao.toFloat(), selectedMatricula)
                        inquilinoDAO.inserir(newInquilino)
                    }else{
                        isInputEmpty = true
                    }
                    //ATUALIZAR LISTA
                    inquilinos = inquilinoDAO.obterTodos()
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
            //DELETAR INQULINO
            Button(
                onClick = {
                    if(selectedCpf.isNotEmpty()){
                        inquilinoDAO.excluir(selectedCpf)
                        //ATUALIZAR LISTA
                        inquilinos = inquilinoDAO.obterTodos()
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
            //EDITAR INQUILINO
            Button(
                onClick = {
                    if(nome.isNotEmpty() && valorCalcao.isNotEmpty()){
                        val newInquilino = Inquilino(selectedCpf, nome, valorCalcao.toFloat(), selectedMatricula)
                        inquilinoDAO.atualizar(newInquilino)
                    }else{
                        isInputEmpty = true
                    }
                    //ATUALIZAR LISTA
                    inquilinos = inquilinoDAO.obterTodos()
                },
                Modifier
                    .padding(
                        top = 8.dp,
                        start = 16.dp,
                        end = 16.dp,
                    )
                    .fillMaxWidth(),
            ) {
                TextoComponent("Editar")
            }
        }
    }
}

@Composable
fun BlocoInquilino(inquilino: Inquilino, selectedCpf: String, onSelectedCpfChange: (String) -> Unit){
    Card(
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .fillMaxWidth()
            .selectable(
                selected = inquilino.cpf == selectedCpf,
                onClick = {
                    if (selectedCpf != inquilino.cpf) {
                        onSelectedCpfChange(inquilino.cpf)
                    } else {
                        onSelectedCpfChange("")
                    }
                }
            ),
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
        border = BorderStroke(2.dp, color = Color.Black),
        colors = CardDefaults.cardColors(
            containerColor = if(inquilino.cpf == selectedCpf) {
                MaterialTheme.colorScheme.tertiary
            }else{
                MaterialTheme.colorScheme.primary
            }
        )
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            ImagemCardComponent()
            Column {
                TextoBoldComponent("CPF: " + inquilino.cpf)
                TextoBoldComponent("Nome: " + inquilino.nome)
                TextoBoldComponent("Valor Caução: " + inquilino.valorCaucaoDepositado.toString())
                TextoBoldComponent("Matricula Imovel: " + inquilino.imovel)
            }
        }
    }

}
